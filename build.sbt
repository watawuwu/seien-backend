name := """seien"""

version := "1.0-SNAPSHOT"

lazy val infra = (project in file("modules/infra"))
  .enablePlugins(PlayScala)

lazy val domain = (project in file("modules/domain"))
  .enablePlugins(PlayScala)
  .dependsOn(infra)

lazy val appli = (project in file("modules/appli"))
  .enablePlugins(PlayScala)
  .dependsOn(domain, infra)

lazy val main = (project in file(".")).enablePlugins(PlayScala)
  .aggregate(domain, infra, appli)
  .dependsOn(domain, infra, appli)

BuildSettings.mainSettings


