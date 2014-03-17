package com.phlexglobal

import java.io.File
import java.io.FileNotFoundException
import org.scalatest._

class TomcatProjectTest extends FlatSpec with Matchers {

  import TomcatPlugin.Credentials

  "The TomcatProject" should "return an ok message on successful redeploy" in {
    val result = TomcatProject.redeploy(
      "localhost",
      8080,
      new File("src/test/resources/my-webapp.war"),
      Credentials("dgolding", "tomcat")
    )
    assert((200 to 300) contains result)
  }

  it should "throw a not found exception if the war file is missing" in {
    intercept[FileNotFoundException] {
      TomcatProject.redeploy(
        "localhost",
        8080,
        new File("src/test/resources/missing.war"),
        Credentials("dgolding", "tomcat")
      )
    }
  }

  it should "throw an exception if the app exists on deployment" in {
    intercept[Exception] {
      TomcatProject.deploy(
        "localhost",
        8080,
        new File("src/test/resources/my-webapp.war"),
        Credentials("dgolding", "tomcat")
      )
    }
  }

}

