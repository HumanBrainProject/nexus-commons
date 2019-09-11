package ch.epfl.bluebrain.nexus.commons.service.stream

import akka.Done
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Status}
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings}
import akka.event.Logging
import akka.pattern.pipe
import akka.stream.scaladsl.{Keep, RunnableGraph, Sink, Source}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, KillSwitches, Supervision, UniqueKillSwitch}
import ch.epfl.bluebrain.nexus.commons.service.stream.SingletonStreamCoordinator._
import shapeless.Typeable

import scala.concurrent.{ExecutionContext, Future}

/**
  * Actor implementation that builds and manages a stream ([[RunnableGraph]]).  It's purpose is to be run as a singleton
  * actor in clustered deployments.
  *
  * @param init   an initialization function to be run when the actor starts, or when the stream is restarted
  * @param source an initialization function that produces a stream from an initial start value
  */
class SingletonStreamCoordinator[A: Typeable, E](init: () => Future[A], source: A => Source[E, _])
    extends Actor
    with ActorLogging {

  private val A = implicitly[Typeable[A]]
  implicit private val as: ActorSystem = context.system
  implicit private val ec: ExecutionContext = context.dispatcher
  private val logging = Logging(as, SingletonStreamCoordinator.getClass)

  val decider: Supervision.Decider = { e =>
    logging.error("Unhandled exception in stream", e)
    Supervision.Stop
  }
  val materializerSettings = ActorMaterializerSettings(as).withSupervisionStrategy(decider)

  implicit private val mt: ActorMaterializer = ActorMaterializer(materializerSettings)

  private def initialize(): Unit = {
    val _ = init().map(Start).recover { case _ => initialize() } pipeTo self
  }

  override def preStart(): Unit = {
    super.preStart()
    initialize()
  }

  private def buildStream(a: A): RunnableGraph[(UniqueKillSwitch, Future[Done])] =
    source(a)
      .viaMat(KillSwitches.single)(Keep.right)
      .toMat(Sink.ignore)(Keep.both)

  override def receive: Receive = {
    case Start(any) =>
      A.cast(any) match {
        case Some(a) =>
          log.info(
            "Received initial start value of type '{}', running the indexing function across the element stream",
            A.describe
          )
          val (killSwitch, doneFuture) = buildStream(a).run()
          doneFuture pipeTo self
          context.become(running(killSwitch))
        // $COVERAGE-OFF$
        case _ =>
          log.error("Received unknown initial start value '{}', expecting type '{}', stopping", any, A.describe)
          context.stop(self)
        // $COVERAGE-ON$
      }
    // $COVERAGE-OFF$
    case Stop =>
      log.info("Received stop signal while waiting for a start value, stopping")
      context.stop(self)
    // $COVERAGE-ON$
  }

  private def running(killSwitch: UniqueKillSwitch): Receive = {
    case Done =>
      log.error("Stream finished unexpectedly, restarting")
      killSwitch.shutdown()
      initialize()
      context.become(receive)
    // $COVERAGE-OFF$
    case Status.Failure(th) =>
      log.error(th, "Stream finished unexpectedly with an error")
      killSwitch.shutdown()
      initialize()
      context.become(receive)
    // $COVERAGE-ON$
    case Stop =>
      log.info("Received stop signal, stopping stream")
      killSwitch.shutdown()
      context.become(stopping)
  }

  private def stopping: Receive = {
    case Done =>
      log.info("Stream finished, stopping")
      context.stop(self)
    // $COVERAGE-OFF$
    case Status.Failure(th) =>
      log.error("Stream finished with an error", th)
      context.stop(self)
    // $COVERAGE-ON$
  }
}

object SingletonStreamCoordinator {
  final private[service] case class Start(any: Any)
  final case object Stop

  /**
    * Builds a [[Props]] for a [[SingletonStreamCoordinator]] with it cluster singleton configuration.
    *
    * @param init   an initialization function to be run when the actor starts, or when the stream is restarted
    * @param source an initialization function that produces a stream from an initial start value
    */
  // $COVERAGE-OFF$
  final def props[A: Typeable, E](init: () => Future[A], source: A => Source[E, _])(implicit as: ActorSystem): Props =
    ClusterSingletonManager.props(
      Props(new SingletonStreamCoordinator(init, source)),
      terminationMessage = Stop,
      settings = ClusterSingletonManagerSettings(as)
    )

  /**
    * Builds a cluster singleton actor of type [[SingletonStreamCoordinator]].
    *
    * @param init   an initialization function to be run when the actor starts, or when the stream is restarted
    * @param source an initialization function that produces a stream from an initial start value
    */
  final def start[A: Typeable, E](init: () => Future[A], source: A => Source[E, _], name: String)(
    implicit as: ActorSystem
  ): ActorRef =
    as.actorOf(props(init, source), name)
  // $COVERAGE-ON$
}