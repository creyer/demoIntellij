name := "demo-intellij"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "org.monifu" %% "monifu" % "1.0-RC3",
  "joda-time" % "joda-time" % "2.9.1",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.joda" % "joda-convert" % "1.2"
)
