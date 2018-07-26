package ch.epfl.bluebrain.nexus.commons.forward.client

//import akka.http.scaladsl.model.StatusCodes.{ClientError, ServerError}
import akka.http.scaladsl.model.{HttpResponse, StatusCode}
import cats.MonadError
import cats.syntax.functor._
import ch.epfl.bluebrain.nexus.commons.http.HttpClient.UntypedHttpClient
import ch.epfl.bluebrain.nexus.commons.types.{Err, RetriableErr}

@SuppressWarnings(Array("IncorrectlyNamedExceptions"))
trait ForwardFailure extends Err {

  /**
    * the HTTP response payload
    */
  def body: String
}

@SuppressWarnings(Array("IncorrectlyNamedExceptions"))
object ForwardFailure {

  /**
    * Generates a Forward failure from the HTTP response .
    *
    * @param r the HTTP response
    */
  def fromResponse[F[_]](r: HttpResponse)(implicit cl: UntypedHttpClient[F],
                                          F: MonadError[F, Throwable]): F[ForwardFailure] =
    cl.toString(r.entity).map(body => fromStatusCode(r.status, body))


  /**
    * Generates a ElasticSearch server failure from the HTTP response status ''code''.
    *
    * @param code the HTTP response status ''code''
    * @param body the HTTP response payload
    */
  def fromStatusCode(code: StatusCode, body: String): ForwardFailure =
    code match {
      case _              => ForwardError(code, body)
    }

  /**
    * An unexpected failure when attempting to communicate with a ElasticSearch endpoint.
    *
    * @param status the status returned by the ElasticSearch endpoint
    * @param body   the response body returned by the ElasticSearch endpoint
    */
  final case class ForwardError(status: StatusCode, body: String)
    extends RetriableErr(s"Unexpected error with status code '$status'")
      with ForwardFailure
}