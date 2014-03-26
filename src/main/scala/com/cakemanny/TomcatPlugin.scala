package com.cakemanny

import sbt._
import Keys._

object TomcatPlugin extends Plugin {

  case class TomcatCredentials(
    username: String,
    password: String
  )

  object TomcatKeys {
    val redeploy    = TaskKey[Unit]("redeploy", "Deploy a war file to the tomcat server")
    val host        = SettingKey[String]("host", "The hostname of the tomcat instance to deploy to.")
    val port        = SettingKey[Int]("port", "The port of the tomcat instance is running on.")
    val warFile     = SettingKey[File]("war-file", "The war file to be deployed")
    val credentials = SettingKey[TomcatCredentials]("Credentials used to authenticate with the tomcat server")
  }

  import TomcatKeys._

  def redeployTask: Def.Initialize[Task[Unit]] =
    (streams, host, port, warFile, credentials) map {
      (out, host, port, war, creds) =>
        out.log("redeploy") info (s"About to deploy $war to $host:$port")
        TomcatProject.redeploy(host, port, war, creds)
    }

  def tomcatSettings: Seq[Setting[_]] = Seq(
    host       := "localhost",
    port       := 8080,
    redeploy  <<= redeployTask
  )

}

