import Dependencies._

ThisBuild / organization := "com.adrack"
ThisBuild / scalaVersion := "2.13.10"

// -------------------------------------------------------------------------------------------------------------------
// Root Project
// -------------------------------------------------------------------------------------------------------------------
lazy val `adrack` =
  project
    .in(file("."))
    .settings(name := "adrack")
    .aggregate(`ad-core`, `ad-repo`, `ad-api`)
    .dependsOn(`ad-core`, `ad-repo`, `ad-api`)

// -------------------------------------------------------------------------------------------------------------------
// Core Module
// -------------------------------------------------------------------------------------------------------------------
lazy val `ad-core` = project
  .in(file("modules/ad-core"))
  .settings(name := "modules/ad-core")
  .settings(commonSettings)
  .settings(dependencies)

// -------------------------------------------------------------------------------------------------------------------
// Repo Module
// -------------------------------------------------------------------------------------------------------------------
lazy val `ad-repo` = project
  .in(file("modules/ad-repo"))
  .settings(name := "modules/ad-repo")
  .settings(commonSettings)
  .settings(dependencies)

// -------------------------------------------------------------------------------------------------------------------
// API Module
// -------------------------------------------------------------------------------------------------------------------
lazy val `ad-api` = project
  .in(file("modules/ad-api"))
  .settings(name := "modules/ad-api")
  .settings(commonSettings)
  .settings(dependencies)

lazy val commonSettings =
  compilerPlugins ++ commonScalacOptions ++ Seq(
    update / evictionWarningOptions := EvictionWarningOptions.empty
  )

lazy val compilerPlugins = Seq(
  addCompilerPlugin(com.olegpy.`better-monadic-for`),
  addCompilerPlugin(org.augustjune.`context-applied`),
  addCompilerPlugin(org.typelevel.`kind-projector`),
)

lazy val commonScalacOptions = Seq(
  Compile / console / scalacOptions := {
    (Compile / console / scalacOptions)
      .value
      .filterNot(_.contains("wartremover"))
      .filterNot(Scalac.Lint.toSet)
      .filterNot(Scalac.FatalWarnings.toSet) :+ "-Wconf:any:silent"
  },
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value,
)

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    // main dependencies
  ),
  libraryDependencies ++= Seq(
    com.github.alexarchambault.`scalacheck-shapeless_1.15`,
    org.scalacheck.scalacheck,
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-15`,
    org.typelevel.`discipline-scalatest`,
  ).map(_ % Test),
)
