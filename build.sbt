sbtPlugin := true

name := "sbt-tomcat-plugin"

organization := "com.phlexglobal"

version := "0.1"

publishArtifact in (Compile, packageDoc) := false

libraryDependencies += "org.glassfish.jersey.core" % "jersey-client" % "2.7"

