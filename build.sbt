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

lazy val commonDeps = libraryDependencies ++= Seq(
  "com.github.scopt" %% "scopt"         % "4.0.0-RC2",
  "com.github.Ma27"  %% "rediscala"     % "1.9.1",
  "redis.clients"    % "jedis"          % "3.3.0",
  "com.huaban"       % "jieba-analysis" % "1.0.2",
  "org.mongodb"      % "mongodb-driver" % "3.12.4",
  "org.mongodb"      %% "casbah-core"   % "3.1.1"
)

lazy val sparkDeps = libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core"                          % Version.spark,
  "org.apache.spark" %% "spark-streaming"                     % Version.spark,
  "org.apache.spark" %% "spark-streaming-flume"               % Version.spark,
  "org.apache.bahir" %% "spark-streaming-mqtt"                % Version.bahir,
  "org.apache.bahir" %% "spark-streaming-zeromq"              % Version.bahir,
  "org.apache.bahir" %% "spark-streaming-twitter"             % Version.bahir,
  "org.apache.spark" %% "spark-mllib"                         % Version.mllib,
  "org.apache.spark" %% "spark-sql"                           % Version.spark,
  "org.apache.spark" %% "spark-hive"                          % Version.spark,
  "org.apache.spark" %% "spark-graphx"                        % Version.spark,
  "org.apache.spark" %% "spark-streaming-kafka-0-10-assembly" % Version.kafka
)

lazy val hadoopDeps = libraryDependencies ++= Seq(
  "com.google.guava"  % "guava"            % Version.guava,
  "org.apache.hadoop" % "hadoop-core"      % "1.2.1",
  "org.apache.hadoop" % "hadoop-mapred"    % "0.22.0",
  "org.apache.hadoop" % "hadoop-mapreduce" % "3.2.1",
  "org.apache.hadoop" % "hadoop-common"    % Version.hadoop
)

lazy val hbaseDeps = libraryDependencies ++= Seq(
  "org.apache.hbase" % "hbase"           % Version.hbase,
  "org.apache.hbase" % "hbase-common"    % Version.hbase,
  "org.apache.hbase" % "hbase-client"    % Version.hbase,
  "org.apache.hbase" % "hbase-mapreduce" % Version.hbase
)

lazy val root = (project in file("."))
  .settings(
    organization := "Neurodyne",
    name := "top",
    version := "0.0.1",
    scalaVersion := "2.12.11",
    maxErrors := 3,
    commonSettings,
    commonDeps,
    hadoopDeps,
    hbaseDeps,
    sparkDeps
  )

// Aliases
addCommandAlias("rel", "reload")
addCommandAlias("com", "all compile test:compile it:compile")
addCommandAlias("fix", "all compile:scalafix test:scalafix")
addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")

scalafixDependencies in ThisBuild += "com.nequissimus" %% "sort-imports" % "0.5.0"
