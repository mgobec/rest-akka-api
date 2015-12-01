import scalariform.formatter.preferences._

name          := "rest-akka-api"
licenses      += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
version       := "0.0.1"
scalaVersion  := "2.11.7"
scalacOptions ++= List(
  "-unchecked",
  "-feature",
  "-deprecation",
  "-encoding",
  "utf8"
)

unmanagedSourceDirectories.in(Compile) := List(scalaSource.in(Compile).value)
unmanagedSourceDirectories.in(Test)    := List(scalaSource.in(Test).value)

libraryDependencies ++= {
  val AkkaStreams     = "2.0-M2"
  val JacksonVersion  = "3.3.0"

  Seq(
    "com.typesafe.akka"       %% "akka-stream-experimental"           % AkkaStreams,
    "com.typesafe.akka"       %% "akka-http-experimental"             % AkkaStreams,
    "com.typesafe.akka"       %% "akka-http-core-experimental"        % AkkaStreams,
    "com.typesafe.akka"       %% "akka-http-xml-experimental"         % AkkaStreams,
    "com.typesafe.akka"       %% "akka-http-spray-json-experimental"  % AkkaStreams,
    "org.json4s"              %% "json4s-jackson"                     % JacksonVersion,
    "org.json4s"              %% "json4s-ext"                         % JacksonVersion
  )
}

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)