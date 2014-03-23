sbtPlugin := true

name := "sbt-tomcat-plugin"

organization := "com.cakemanny"

version := "0.2-SNAPSHOT"

publishArtifact in (Compile, packageDoc) := false

libraryDependencies ++= Seq(
  "org.glassfish.jersey.core" % "jersey-client" % "2.7" exclude("org.glassfish.hk2","hk2-utils") exclude("org.glassfish.hk2","hk2-locator") exclude("javax.validation","validation-api"),
  "org.glassfish.hk2" % "hk2-locator" % "2.2.0",
  "org.glassfish.hk2" % "hk2-utils" % "2.2.0",
  "javax.validation" % "validation-api" % "1.1.0.Final",
  "junit" % "junit" % "4.11" % "test",
  "org.scalatest" %% "scalatest" % "2.1.0" % "test"
)

