name := "PriceCheckingServer"

version := "1.0"

scalaVersion := "2.12.3"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
  "org.apache.derby" % "derby" % "10.13.1.1",
  "org.scalikejdbc" %% "scalikejdbc" % "3.1.0",
  "com.h2database"  %  "h2" % "1.4.196",
  "ch.qos.logback"  %  "logback-classic" % "1.2.3"
)

fork := true
