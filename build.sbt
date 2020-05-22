resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

lazy val commonSettings = Seq(
// Refine scalac params from tpolecat
  scalacOptions --= Seq(
    "-Xfatal-warnings"
  )
)

lazy val sparkDeps = libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core"              % Version.spark,
  "org.apache.spark" %% "spark-streaming"         % Version.spark,
  "org.apache.spark" %% "spark-streaming-flume"   % Version.spark,
  "org.apache.bahir" %% "spark-streaming-mqtt"    % Version.bahir,
  "org.apache.bahir" %% "spark-streaming-zeromq"  % Version.bahir,
  "org.apache.bahir" %% "spark-streaming-twitter" % Version.bahir,
  "org.apache.spark" %% "spark-sql"               % Version.spark,
  "org.apache.spark" %% "spark-hive"              % Version.spark,
  "org.apache.spark" %% "spark-graphx"            % Version.spark
)

lazy val hadoopDeps = libraryDependencies ++= Seq(
  "com.google.guava"  %% "guava"         % Version.guava,
  "org.apache.hadoop" %% "hadoop-common" % Version.hadoop,
  "org.apache.hadoop" %% "hadoop-mapred" % "0.22.0",
  "org.apache.hbase"  %% "hbase-common"  % Version.hbase,
  "org.apache.hbase"  %% "hbase-client"  % Version.hbase
)

lazy val root = (project in file("."))
  .settings(
    organization := "Neurodyne",
    name := "top",
    version := "0.0.1",
    scalaVersion := "2.12.10",
    maxErrors := 3,
    commonSettings,
    sparkDeps
  )

// Aliases
addCommandAlias("rel", "reload")
addCommandAlias("com", "all compile test:compile it:compile")
addCommandAlias("fix", "all compile:scalafix test:scalafix")
addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")

scalafixDependencies in ThisBuild += "com.nequissimus" %% "sort-imports" % "0.5.0"
