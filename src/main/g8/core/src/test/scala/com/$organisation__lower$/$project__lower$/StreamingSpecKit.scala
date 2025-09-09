package com.$organisation;format="lower"$.$project;format="lower"$

import scala.concurrent.ExecutionContext

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.Materializer

/**
 * Superclass for tests which need to materialise streams
 *
 * Based on [[ActorSpecKit]] which already provides us with a managed actor system, this just
 * includes a materializer as well to make us ready to materialise streams.
 */
abstract class StreamingSpecKit(sys: ActorSystem) extends ActorSpecKit(sys) {
  override implicit val ec: ExecutionContext = sys.dispatcher

  override def afterAll(): Unit = {
    implicitly[Materializer].shutdown()
    super.afterAll()
  }
}
