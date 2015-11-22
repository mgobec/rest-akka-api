package com.rest.akka.api.configuration

import com.typesafe.config.ConfigFactory

trait AppSettings {
  private val config = ConfigFactory.load()
  private val main = config.getConfig("main")
  private val httpConfig = config.getConfig("http")

  val AppName = main.getString("app-name")

  val httpInterface = httpConfig.getString("interface")
  val httpPort = httpConfig.getInt("port")
}
