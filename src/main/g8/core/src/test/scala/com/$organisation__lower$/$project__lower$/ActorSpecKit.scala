package com.$organisation;format="lower"$.$project;format="lower"$

import akka.actor.ActorSystem
import akka.testkit._
import org.scalatest._

/**
 * Superclass for tests which use actor systems
 *
 * Extends the akka testkit's [[akka.testkit.TestKit]] class to to ensure the actor system gets
 * shut down when we're finished, and to mix in our standard [[SpecKit]] as well
 */
abstract class ActorSpecKit(sys: ActorSystem)
  extends TestKit(sys)
  with SpecKit
  with BeforeAndAfterAll
  with ImplicitSender {

  override def afterAll(): Unit = TestKit.shutdownActorSystem(system)
}
