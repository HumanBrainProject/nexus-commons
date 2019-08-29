package ch.epfl.bluebrain.nexus.commons.forward.client

import akka.http.scaladsl.client.RequestBuilding._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.Uri.{Path, Query}
import cats.MonadError
import cats.syntax.applicativeError._
import cats.syntax.flatMap._
//import cats.syntax.functor._
//import ch.epfl.bluebrain.nexus.commons.forward.client.ForwardBaseClient._
//import ch.epfl.bluebrain.nexus.commons.forward.client.ForwardClient._
import ch.epfl.bluebrain.nexus.commons.http.HttpClient.UntypedHttpClient
import ch.epfl.bluebrain.nexus.commons.http.{HttpClient, UnexpectedUnsuccessfulHttpResponse}
//import ch.epfl.bluebrain.nexus.commons.types.search._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.Json

import scala.concurrent.ExecutionContext

/**
  * ElasticSearch client implementation that uses a RESTful API endpoint for interacting with a ElasticSearch deployment.
  *
  * @param base        the base uri of the ForwardSearch endpoint
  * @tparam F the monadic effect type
  */
class ForwardClient[F[_]](val base: Uri)(
  implicit
  cl: UntypedHttpClient[F],
  ec: ExecutionContext,
  F: MonadError[F, Throwable]
) extends ForwardBaseClient[F] {

  /**
    * Build a creation request with the provided ''id'' and ''payload''
    *
    * @param fullId    the full id of the document to update (may contain path and rev). Up to the caller to format it
    *                  properly. It is not interpreted by the forward client
    * @param payload the document's payload
    */
  def create(
    fullId: String,
    payload: Json,
    authorIdOpt: Option[String] = None,
    eventDateTimeOpt: Option[String] = None
  ): F[Unit] = {
    val queryParameters = Map("authorId" -> authorIdOpt, "eventDateTime" -> eventDateTimeOpt)
      .collect { case (key, Some(value)) => (key, value) }
    val uri = base.copy(path = base.path ++ Path(fullId)).withQuery(Query(queryParameters))

    log.info(s"forward client - creation id: ${fullId}")
    execute(Post(uri, payload), Set(OK, Created), "forward creation")
  }

  /**
    * Updates an existing document with the provided payload.
    *
    * @param fullId    the full id of the document to update (may contain path and rev). Up to the caller to format it
    *                  properly. It is not interpreted by the forward client
    * @param payload   the document's payload
    */
  def update(
    fullId: String,
    payload: Json,
    authorIdOpt: Option[String] = None,
    eventDateTimeOpt: Option[String] = None
  ): F[Unit] = {
    val queryParameters = Map("authorId" -> authorIdOpt, "eventDateTime" -> eventDateTimeOpt)
      .collect { case (key, Some(value)) => (key, value) }
    val uri = base.copy(path = base.path ++ Path(fullId)).withQuery(Query(queryParameters))

    log.info(s"forward client - update id: ${fullId}")
    execute(Put(uri, payload), Set(OK), "forward update")
  }

  /**
    * Deletes the document with the provided ''id''
    *
    * @param fullId    the full id of the document to update (may contain path and rev). Up to the caller to format it
    *                  properly. It is not interpreted by the forward client
    */
  def delete(
    fullId: String,
    revOpt: Option[String] = None,
    authorIdOpt: Option[String] = None,
    eventDateTimeOpt: Option[String] = None
  ): F[Unit] = {
    val queryParameters = Map("authorId" -> authorIdOpt, "eventDateTime" -> eventDateTimeOpt, "rev" -> revOpt)
      .collect { case (key, Some(value)) => (key, value) }
    val uri = base.copy(path = base.path ++ Path(fullId)).withQuery(Query(queryParameters))

    log.info(s"forward client - creation id: ${fullId}")
    execute(Delete(uri), Set(OK), "forward delete")
  }

  /**
    * Fetch a document with the provided ''id''
    *
    * @param fullId      the id of the document to fetch
    */
  def get[A](fullId: String)(implicit rs: HttpClient[F, A]): F[A] = {
    log.info(s"forward client - get id: ${fullId}")
    val uri = base.copy(path = base.path ++ Path(fullId))
    rs(Get(uri)).recoverWith {
      case UnexpectedUnsuccessfulHttpResponse(r) => ForwardFailure.fromResponse(r).flatMap(F.raiseError)
      case other                                 => F.raiseError(other)
    }
  }

}

object ForwardClient {

  /**
    * Construct a [[ForwardClient]] from the provided ''base'' uri and the provided query client
    *
    * @param base        the base uri of the Forward endpoint
    * @tparam F the monadic effect type
    */
  final def apply[F[_]](base: Uri)(
    implicit
    cl: UntypedHttpClient[F],
    ec: ExecutionContext,
    F: MonadError[F, Throwable]
  ): ForwardClient[F] =
    new ForwardClient(base)

}
