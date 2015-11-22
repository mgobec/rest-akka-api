package com.rest.akka.api.routes

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import com.rest.akka.api.serialization.JsonSerialization
import org.joda.time.DateTime

/**
 * Created by matija on 11/22/2015.
 */
class MainRouter extends JsonSerialization {

  def apply(): Route = {
    //    handleExceptions(exceptionHandler) {
    pathPrefix("api") { routes() }
    //    }
  }

  private def routes(): Route =
    path("test") { get {
      complete(
        <html>
          <body>Congrats, it works!!</body>
        </html>
      ) }
    } ~
    path("status") { get { complete(Status("OK", DateTime.now().toString("HH:mm dd MMM yyyy"))) } }
}

case class Status(status: String, time: String)