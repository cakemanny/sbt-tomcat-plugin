
# Tomcat Plugin
A simple sbt plugin that allows redeployment of a web project to tomcat.
Thanks to the numerous examples on github I've managed to write this on the
second and third day of using sbt - not being able to find one straight away.

## Instructions

First build and publish locally
```
> compile
...
[success] Total time: 14s, completed...
> publish-local
...
[info] published ivy to /Users/you/.ivy2/local/com.cakemanny/sbt-tomcat-plugin/scala_...
```

Add to *project/plugins.sbt*:
```scala
addSbtPlugin("com.cakemanny" %% "sbt-tomcat-plugin" % "0.2-SNAPSHOT")
```

Configure in *build.sbt*:
```scala
TomcatKeys.credentials := TomcatCredentials("freddy", "freddyspassword")

TomcatKeys.warFile := file ("target/scala-2.10/myapp.war")

seq(tomcatSettings: _*)
```

## Configuring Tomcat
To be able to perform automatic deployment the *manager-script* role needs
to be added to your tomcat profile in *conf/tomcat-users.xml*
```xml
<tomcat-users>
  <role rolename="manager-script"/>
  <user username="freddy" password="freddyspassword" roles="manager-script"/>
</tomcat-users>
```

