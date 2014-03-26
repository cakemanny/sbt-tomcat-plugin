sbtPlugin := true

name := "sbt-tomcat-plugin"

organization := "com.cakemanny"

version := "0.2-SNAPSHOT"

publishArtifact in (Compile, packageDoc) := false

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.0" % "test"

