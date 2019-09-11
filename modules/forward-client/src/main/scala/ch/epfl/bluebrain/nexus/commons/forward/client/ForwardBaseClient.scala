package ch.epfl.bluebrain.nexus.commons.forward.client

import akka.http.scaladsl.model.{HttpRequest, StatusCode}
import cats.MonadError
import cats.syntax.flatMap._
import ch.epfl.bluebrain.nexus.commons.http.HttpClient.{HttpResponseSyntax, UntypedHttpClient}
import journal.Logger

/**
  * ForwardBaseClient provides the common methods and vals used for forward clients.
  *
  * @tparam F the monadic effect type
  */
abstract class ForwardBaseClient[F[_]](
  implicit
  cl: UntypedHttpClient[F],
  F: MonadError[F, Throwable]
) {
  private[client] val log = Logger[this.type]

  private[client] def execute(req: HttpRequest, expectedCodes: Set[StatusCode]): F[Unit] =
    executeWith(req, expectedCodes, None)

  private[client] def execute(req: HttpRequest, expectedCodes: Set[StatusCode], intent: => String): F[Unit] =
    executeWith(req, expectedCodes, Some(intent))

  private def executeWith(req: HttpRequest, expectedCodes: Set[StatusCode], intent: => Option[String]): F[Unit] = {
    log.info(s"EXECUTE WITH - request: ${req.toString()}")
    cl(req).discardOnCodesOr(expectedCodes) { resp =>
      log.info(s"EXECUTE INNER - response: ${resp.toString()}")
      ForwardFailure.fromResponse(resp).flatMap { f =>
        val _ = intent.map(
          msg =>
            log.error(
              s"Unexpected Service response for intent '$msg':\nRequest: '${req.method} ${req.uri}'\nStatus: '${resp.status}'\nResponse: '${f.body}'"
            )
        )
        F.raiseError(f)
      }
    }
  }
}

object ForwardBaseClient {}
