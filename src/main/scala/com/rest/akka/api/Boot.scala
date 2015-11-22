package com.rest.akka.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.rest.akka.api.configuration.AppSettings
import com.rest.akka.api.routes.MainRouter

object Boot extends App with AppSettings {

  protected implicit val system = ActorSystem(AppName)
  protected implicit val materializer = ActorMaterializer()
  import system.dispatcher

  protected val mainRouter = new MainRouter
  protected val bindingFuture = Http().bindAndHandle(mainRouter(), httpInterface, httpPort)
  bindingFuture.onFailure {
    case e =>
      Console.println(s"Server binding failed with ${e.getMessage}")
      system.shutdown()
  }

  Console.println(s"Server online at ${httpInterface}:${httpPort}")
  Console.println(s"Press RETURN to stop...")
  scala.io.StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.shutdown()) // and shutdown when done
}
