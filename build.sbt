name := "jFin"

version := "3.0.0"

scalaVersion := "2.9.2"

libraryDependencies ++= Seq (
	"org.scalaz" %% "scalaz-core" % "6.0.4" % "compile" withSources(),
    "org.specs2" %% "specs2" % "1.9" % "test",
    "org.mockito" % "mockito-core" % "1.9.0" % "test",
    "junit" % "junit" % "4.10" % "test",
    "joda-time" % "joda-time" % "1.6",
    "org.scala-tools.time" % "time_2.8.0" % "0.2"
)