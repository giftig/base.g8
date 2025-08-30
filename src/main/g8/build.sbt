// Latest open source versions, to go higher will require swapping to pekko instead
val akkaCoreVersion = "2.6.19"
val akkaHttpVersion = "10.2.10"

lazy val coreDeps = Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor" % akkaCoreVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaCoreVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaCoreVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "io.spray" %% "spray-json" % "1.3.5"
)

lazy val coreTestDeps = Seq(
  "com.typesafe.akka" %% "akka-testkit" % akkaCoreVersion % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "org.scalatest" %% "scalatest" % "3.2.0" % Test
)

lazy val allCoreDeps = coreDeps ++ coreTestDeps

// CI should use fatal warnings, but if you want to turn that off locally to avoid warnings being
// promoted to errors while you're still getting your code compiling, you can set this env var
lazy val useFatalWarnings = !sys.env.get("$organisation;format="upper"$_NO_FATAL_WARNINGS").contains("1")
lazy val fatalWarnings = if (useFatalWarnings) Some("-Xfatal-warnings") else None

// Easy tweaking of scale factor because our build might run on a very slow CI box
// More info on scale factor at https://www.scalatest.org/user_guide/using_scalatest_with_sbt
lazy val scalatestScaleFactor = sys.env.getOrElse("SCALATEST_SCALE_FACTOR", "1.0")

lazy val compilerOptions = Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-Xlint",
  "-Ywarn-unused:implicits",
  "-Ywarn-unused:imports",
  "-Ywarn-unused:patvars",
  "-Ywarn-unused:privates",
  "-Ywarn-dead-code"
) ++ fatalWarnings

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  organization := "$domain;format="dot-reverse"$",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.13.6",
  testOptions ++= Seq(
    Tests.Setup { cl =>
      // Workaround to avoid slf4j complaining about multi-threading during initialisation
      // Gets a root logger at the start of test runs to force it to finish initialising first
      cl.loadClass("org.slf4j.LoggerFactory")
        .getMethod("getLogger", cl.loadClass("java.lang.String"))
        .invoke(null, "ROOT")
    },
    Tests.Argument(TestFrameworks.ScalaTest, "-F", scalatestScaleFactor)
  ),
  Test / publishArtifact := true,
  autoAPIMappings := true
)

lazy val docsRoot = "https://docs.$domain$"

// FIXME: Set up as a multi-module build by default, to make it easier to build one. Just simplify
// the structure and move the service root tree out to root if you want a single module
lazy val core = {
  (project in file("core"))
    .settings(
      name := "$project;format="norm"$-core",
      libraryDependencies ++= allCoreDeps,
      retrieveManaged := true,
      apiURL := Some(url(s"\$docsRoot/$project;format="norm"$-core/")),
      commonSettings
    )
}

lazy val service = {
  (project in file("service"))
    .settings(
      name := "$project;format="norm"$-service",
      apiURL := Some(url(s"\$docsRoot/$project;format="norm"$-service/")),
      commonSettings
    )
    .dependsOn(core)
}

lazy val root = {
  (project in file("."))
    .aggregate(core, service)
    .settings(publishArtifact := false)
}
