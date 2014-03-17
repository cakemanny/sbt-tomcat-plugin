sbtPlugin := true

name := "sbt-tomcat-plugin"

organization := "com.phlexglobal"

version := "0.2-SNAPSHOT"

publishArtifact in (Compile, packageDoc) := false

libraryDependencies ++= Seq(
  "org.glassfish.jersey.core" % "jersey-client" % "2.7",
  "junit" % "junit" % "4.11" % "test",
  "org.scalatest" %% "scalatest" % "2.1.0" % "test"
)

