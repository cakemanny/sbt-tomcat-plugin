package com.phlexglobal

import sbt._
import Keys._

object TomcatPlugin extends Plugin {

  case class Credentials(
    username: String,
    password: String
  )

  object TomcatKeys {
    val redeploy    = TaskKey[Unit]("redeploy", "Deploy a war file to the tomcat server")
    val host        = SettingKey[String]("host", "The hostname of the tomcat instance to deploy to.")
    val port        = SettingKey[Int]("port", "The port of the tomcat instance is running on.")
    val warFile     = SettingKey[File]("war-file", "The war file to be deployed")
    val credentials = SettingKey[Credentials]("Credentials used to authenticate with the tomcat server")
  }

  import TomcatKeys._

  def redeployTask: Def.Initialize[Task[Unit]] =
    (streams, host, port, warFile, credentials) map {
      (out, host, port, war, creds) =>
        out.log(s"About to deploy to $war to $host:$port")
        TomcatProject.redeploy(host, port, war, creds)
    }

  def tomcatSettings: Seq[Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "org.glassfish.jersey.core" % "jersey-client" % "2.7" exclude("org.glassfish.hk2","hk2-utils") exclude("org.glassfish.hk2","hk2-locator") exclude("javax.validation","validation-api"),
      "org.glassfish.hk2" % "hk2-locator" % "2.2.0",
      "org.glassfish.hk2" % "hk2-utils" % "2.2.0",
      "javax.validation" % "validation-api" % "1.1.0.Final"
    ),

    host       := "localhost",
    port       := 8080,
    redeploy  <<= redeployTask
  )

}

