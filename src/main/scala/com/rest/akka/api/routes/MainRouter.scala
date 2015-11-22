package com.rest.akka.api.routes

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import com.rest.akka.api.controller.AkkaHttpController
import com.rest.akka.api.domain.WelcomeMessage
import com.rest.akka.api.serialization.JsonSerialization
import org.joda.time.DateTime

class MainRouter(private implicit val materializer: ActorMaterializer) extends JsonSerialization {

  def apply(): Route = {
    //    handleExceptions(exceptionHandler) {
    pathPrefix("api") { routes() }
    //    }
  }

  private def routes(): Route =
    path("test") { get { complete(WelcomeMessage("Test message")) } } ~
    path("welcome") {
      get { complete { AkkaHttpController.welcome() } } ~
      (post & entity(as[WelcomeMessage])) { messageRequest => complete(AkkaHttpController.welcome(Some(messageRequest.message))) }
    } ~
    path("status") { get { complete(Status("OK", DateTime.now().toString("HH:mm dd MMM yyyy"))) } }
}

case class Status(status: String, time: String)