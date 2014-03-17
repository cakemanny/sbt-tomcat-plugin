package com.phlexglobal

import java.io.File

import javax.ws.rs.core.MediaType
import javax.ws.rs.client._
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature
import org.glassfish.jersey.client.ClientConfig

object TomcatProject {

  import TomcatPlugin.Credentials

  def redeploy(host: String, port: Int, war: File, creds: Credentials) = {
    val authFeature = HttpAuthenticationFeature.basic(creds.username, creds.password)
    val client = ClientBuilder.newClient(
      new ClientConfig().register(authFeature, 1)
    )
    val response = client.target(s"http://$host:$port")
      .path("manager/text/deploy?update=true")
      .request()
      .put(Entity.entity(war, MediaType.MULTIPART_FORM_DATA_TYPE))


      // TODO check respose and throw Exception on bad I guess
  }


}
