package ch.epfl.bluebrain.nexus.commons.forward.client

import akka.http.scaladsl.model.StatusCodes.{ClientError, ServerError}
import akka.http.scaladsl.model.{HttpResponse, StatusCode}
import cats.MonadError
import cats.syntax.functor._
import ch.epfl.bluebrain.nexus.commons.http.HttpClient.UntypedHttpClient
import ch.epfl.bluebrain.nexus.commons.types.{Err, RetriableErr}
import journal.Logger
import org.slf4j.LoggerFactory

@SuppressWarnings(Array("IncorrectlyNamedExceptions"))
trait ForwardFailure extends Err {

  /**
    * the HTTP response payload
    */
  def body: String
}

@SuppressWarnings(Array("IncorrectlyNamedExceptions"))
object ForwardFailure {
  private[commons] val log = LoggerFactory.getLogger(this.getClass)
  private[commons] val logger = Logger[this.type]

  /**
    * Generates a Forward failure from the HTTP response .
    *
    * @param r the HTTP response
    */
  def fromResponse[F[_]](
    r: HttpResponse
  )(implicit cl: UntypedHttpClient[F], F: MonadError[F, Throwable]): F[ForwardFailure] = {
    log.info(s"Forward failure response info - ${r.toString()}")
    logger.info(s"Forward failure response info with different logger - ${r.toString()}")
    cl.akkaLogger.info(s"Forward failure response info with akka logger - ${r.toString()}")
    cl.toString(r.entity).map(body => fromStatusCode(r.status, body))
  }

  /**
    * Generates a Forward server failure from the HTTP response status ''code''.
    *
    * @param code the HTTP response status ''code''
    * @param body the HTTP response payload
    */
  def fromStatusCode(code: StatusCode, body: String): ForwardFailure =
    code match {
      case _: ServerError => ForwardServerError(code, body)
      case _: ClientError => ForwardClientError(code, body)
      case _              => ForwardUnexpectedError(code, body)
    }

  /**
    * An unexpected server failure when attempting to communicate with a Forward endpoint.
    *
    * @param status the status returned by the Forward endpoint
    * @param body   the response body returned by the Forward endpoint
    */
  final case class ForwardServerError(status: StatusCode, body: String)
      extends RetriableErr(s"Server error with status code '$status'")
      with ForwardFailure

  /**
    * An unexpected client failure when attempting to communicate with a Forward endpoint.
    *
    * @param status the status returned by the Forward endpoint
    * @param body   the response body returned by the Forward endpoint
    */
  final case class ForwardClientError(status: StatusCode, body: String)
      extends Err(s"Client error with status code '$status'")
      with ForwardFailure

  /**
    * An unexpected failure when attempting to communicate with a Forward endpoint.
    *
    * @param status the status returned by the Forward endpoint
    * @param body   the response body returned by the Forward endpoint
    */
  final case class ForwardUnexpectedError(status: StatusCode, body: String)
      extends RetriableErr(s"Unexpected error with status code '$status'")
      with ForwardFailure

  /**
    * An unexpected failure when attempting to communicate with a Forward endpoint.
    *
    * @param status the status returned by the Forward endpoint
    * @param body   the response body returned by the Forward endpoint
    */
  final case class ForwardError(status: StatusCode, body: String)
      extends RetriableErr(s"Unexpected error with status code '$status'")
      with ForwardFailure
}
