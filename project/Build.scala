import com.typesafe.sbt.SbtScalariform._
import play.sbt.Play.autoImport._
import sbt.Keys._
import sbt._
import scoverage.ScoverageSbtPlugin
import com.typesafe.sbt.SbtNativePackager.autoImport._
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport._
import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._

object BuildSettings {
  val buildOrg = "com.fuscus"
  val buildScalaVersion = "2.11.7"

  val commonSettings: Seq[Setting[_]] = Seq(
    organization := buildOrg,
    scalaVersion := buildScalaVersion,
    javaOptions ++= Seq("-Xmx2G", "-Xms512M", "-XX:+CMSClassUnloadingEnabled"),
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint"),
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint", "-Ywarn-dead-code", "-Ywarn-numeric-widen", "-Ywarn-unused", "-Ywarn-value-discard", "-language:reflectiveCalls"),
    //scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:reflectiveCalls"),
    parallelExecution in Test := false,
    testOptions += Tests.Argument(TestFrameworks.Specs2, "console", "junitxml"),
    doc in Compile <<= target.map(_ / "none"),
    crossPaths := false
  ) ++ scalariformSettings

  val mainJavaOptions = Seq("-Dconfig.file=conf/application_test.conf", "-Dlogger.file=conf/logback_test.xml")
  val moduleJavaOptions = Seq("-Dconfig.file=../../conf/application_test.conf", "-Dlogger.file=../../conf/logback_test.xml")

  val mainSettings: Seq[Setting[_]] = commonSettings ++
    Seq(
      resolvers ++= Resolvers.mainResolvers,
      libraryDependencies ++= Dependencies.mainDependencies,
      javaOptions in Test ++= mainJavaOptions,
      ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := "<empty>;Reverse.*;.*?javascript;controllers.debug.*?",
      // Added the tool directory to Universal
      mappings in Universal ++= (baseDirectory(_ / "tool").value ** "*").get.map { f: File =>
        f -> ("tool/" + f.getName)
      },
      maintainer in Docker := "watawuwu",
      dockerBaseImage := "dockerfile/java:oracle-java8",
      dockerRepository := Some("watawuwu")
    )

  val assetSettings: Seq[Setting[_]] = commonSettings

  val infraSettings: Seq[Setting[_]] = commonSettings ++
    Seq(
      resolvers ++= Resolvers.infraResolvers,
      libraryDependencies ++= Dependencies.infraDependencies,
      javaOptions in Test ++= moduleJavaOptions
    )

  val domainSettings: Seq[Setting[_]] = commonSettings ++
    Seq(
      resolvers ++= Resolvers.domainResolvers,
      libraryDependencies ++= Dependencies.domainDependencies,
      javaOptions in Test ++= moduleJavaOptions
    )

  val appSettings: Seq[Setting[_]] = commonSettings ++
    Seq(
      resolvers ++= Resolvers.appResolvers,
      libraryDependencies ++= Dependencies.appDependencies,
      javaOptions in Test ++= moduleJavaOptions
    )
}

object Resolvers {
  val akkaQuartz = "us.theatr" at "http://repo.theatr.us"
  val scalaz = "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
  val resolvers = Seq(akkaQuartz, scalaz)
  val mainResolvers: Seq[Resolver] = resolvers
  val infraResolvers: Seq[Resolver] = resolvers
  val domainResolvers: Seq[Resolver] = resolvers
  val appResolvers: Seq[Resolver] = resolvers
}

object Dependencies {
  val mysqlConnectorJavaVersion = "5.1.35"
  val awsJavaSdkVersion = "1.10.1"

  val dependencies = Seq(
    specs2                    %  Test,
    "com.eaio.uuid"           %  "uuid"                               % "3.2",
    "com.github.tototoshi"    %% "play-json4s-native"                 % "0.4.1",
    "com.github.tototoshi"    %% "play-json4s-test-native"            % "0.4.1" % "test"
//    "org.scalaz"              %% "scalaz-core"                        % "7.1.3",
//    "org.typelevel"           %% "scalaz-contrib-210"                 % "0.2"
  )

  val dbDependencies = Seq(
    "mysql"                %  "mysql-connector-java"                     % "5.1.36",
    "com.typesafe.play"    %% "play-slick"                               % "1.0.0",
    "com.typesafe.play"    %% "play-slick-evolutions"                    % "1.0.0",
    "com.typesafe.slick"   %% "slick-codegen"                            % "3.0.0",
//    "com.h2database"       %  "h2"                                       % "1.4.187",
    "com.github.tototoshi" %% "slick-joda-mapper"                        % "2.0.0"
  )

  val mainDependencies = dependencies ++ Seq(
    evolutions,
    "org.codehaus.janino"     %  "janino"                             % "2.7.8"
  )

  val infraDependencies = dependencies ++ dbDependencies ++ Seq(
    cache,
    "com.amazonaws"           %  "aws-java-sdk"                       % awsJavaSdkVersion,
    "io.backchat.inflector"   %% "scala-inflector"                    % "1.3.5"
  )

  val domainDependencies = dependencies ++ dbDependencies ++ Seq(
    cache,
    "mysql"                   %  "mysql-connector-java"               % mysqlConnectorJavaVersion,
    "com.amazonaws"           %  "aws-java-sdk"                       % awsJavaSdkVersion
  )

  val appDependencies = dependencies ++ dbDependencies ++ Seq(
    cache,
    filters,
    "com.amazonaws"           %  "aws-java-sdk"                       % awsJavaSdkVersion,
    "pl.matisoft"             %% "swagger-play24"                     % "1.4"
  )
}
