package com.$organisation;format="lower"$.$project;format="lower"$

import scala.concurrent.ExecutionContext
import scala.io.Source

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

trait SpecKit
    extends AnyFlatSpecLike
    with Matchers
    with OptionValues
    with EitherValues
    with ScalaFutures {

  implicit val ec: ExecutionContext = ExecutionContext.global

  def fixture(name: String): String = Source.fromResource(s"fixtures/\$name").mkString
}

object SpecKit extends SpecKit
