name := "akka-job-scheduler"

organization := "com.snapswap"

version := "1.0.0"

scalaVersion := "2.11.11"

scalacOptions := Seq(
  "-feature",
  "-unchecked",
  "-deprecation",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture",
  "-Ywarn-unused-import",
  "-encoding",
  "UTF-8")

libraryDependencies ++= {
  val akkaV = "2.4.19"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV % "provided",
    "org.scaldi" %% "scaldi" % "0.5.8" % "provided",
    "com.google.code.findbugs" % "jsr305" % "3.0.1" % "provided",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
}

fork in Test := true
javaOptions in Test += "-Xmx512m"
testOptions in Test += Tests.Argument("-u", "console")
