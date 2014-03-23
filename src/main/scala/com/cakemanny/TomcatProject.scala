package com.cakemanny

import java.io.File
import java.io.FileNotFoundException

import javax.ws.rs.core.MediaType
import javax.ws.rs.client._
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature
import org.glassfish.jersey.client.ClientConfig
import scala.util.matching.Regex

object TomcatProject {

  import TomcatPlugin.TomcatCredentials

  private def validateWar(war: File) =
    if (war.exists)
      war
    else
      throw new FileNotFoundException(war.toString)

  def redeploy(host: String, port: Int, war: File, creds: TomcatCredentials) = {
    deploy(host, port, war, creds, Seq(("update", "true")))
  }

  def deploy(
    host: String,
    port: Int,
    war: File,
    creds: TomcatCredentials,
    extras: Seq[Pair[String, String]] = Seq()
  ) = {
    validateWar(war)

    val authFeature = HttpAuthenticationFeature.basic(creds.username, creds.password)
    val client = ClientBuilder.newClient(
      new ClientConfig().register(authFeature, 1)
    )
    val path = "/" + "\\.war$".r.replaceFirstIn(war.getName, "")
    var target = client.target(s"http://$host:$port")
      .path("manager/text/deploy")
      .queryParam("path", path)

    for (param <- extras)
      target = target.queryParam(param._1, param._2)

    val response = target.request()
      .put(Entity.entity(war, MediaType.MULTIPART_FORM_DATA_TYPE))

    if ((200 to 300) contains response.getStatus) {
      val message = response.readEntity(classOf[String])
      println(message)
      if (message.startsWith("FAIL")) {
        throw new Exception(message)
      } else
        response.getStatus
    } else
      throw new Exception("Unable to deploy: " +
        response.getStatusInfo.getReasonPhrase)
  }

}
