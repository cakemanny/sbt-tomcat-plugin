package com.cakemanny

import java.io.{ File, FileNotFoundException }
import scala.io.Source

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

  // Want to create the plugin with no dependencies
  def deploy(
    host: String,
    port: Int,
    war: File,
    creds: TomcatCredentials,
    extras: Seq[Pair[String, String]] = Seq()
  ) = {
    validateWar(war)

    val path = "/" + war.getName.replaceFirst("\\.war$", "")
    val urlString = extras.foldLeft(
      s"http://$host:$port/manager/text/deploy?path=$path"
    ){ (l, r) => l + "&" + r._1 + "=" + r._2 }
    val url = new java.net.URL(urlString)

    val conn = url.openConnection.asInstanceOf[java.net.HttpURLConnection]
    conn.setRequestMethod("PUT")
    conn.setDoOutput(true)
    conn.setRequestProperty("Content-Length", String.valueOf(war.length))
    conn.setRequestProperty("Content-Type", "application/x-zip")

    val token = new sun.misc.BASE64Encoder().encode(
      (creds.username + ":" + creds.password).getBytes
    )
    conn.setRequestProperty("Authorization", "Basic " + token)

    val warBytes = java.nio.file.Files.readAllBytes(war.toPath)
    conn.getOutputStream.write(warBytes)
    conn.getOutputStream.close

    val message = Source.fromInputStream(conn.getInputStream).mkString
    println(message)
    if (message.startsWith("FAIL")) {
      throw new Exception(message)
    }
    200
  }

}
