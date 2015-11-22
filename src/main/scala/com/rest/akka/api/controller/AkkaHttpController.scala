package com.rest.akka.api.controller

object AkkaHttpController {

  def welcome(message: Option[String] = None) = {
    <html>
      <body>Congrats {message.getOrElse("")}, it works!!</body>
    </html>
  }

}
