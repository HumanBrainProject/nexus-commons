/*
scalafmt: {
  style = defaultWithAlign
  maxColumn = 150
  align.tokens = [
    { code = "=>", owner = "Case" }
    { code = "?", owner = "Case" }
    { code = "extends", owner = "Defn.(Class|Trait|Object)" }
    { code = "//", owner = ".*" }
    { code = "{", owner = "Template" }
    { code = "}", owner = "Template" }
    { code = ":=", owner = "Term.ApplyInfix" }
    { code = "++=", owner = "Term.ApplyInfix" }
    { code = "+=", owner = "Term.ApplyInfix" }
    { code = "%", owner = "Term.ApplyInfix" }
    { code = "%%", owner = "Term.ApplyInfix" }
    { code = "%%%", owner = "Term.ApplyInfix" }
    { code = "->", owner = "Term.ApplyInfix" }
    { code = "?", owner = "Term.ApplyInfix" }
    { code = "<-", owner = "Enumerator.Generator" }
    { code = "?", owner = "Enumerator.Generator" }
    { code = "=", owner = "(Enumerator.Val|Defn.(Va(l|r)|Def|Type))" }
  ]
}
 */
val wesoValidatorVersion            = "0.0.65-nexus1"
val metricsCoreVersion              = "3.2.6"
val jenaVersion                     = "3.6.0"
val blazegraphVersion               = "2.1.4"
val jacksonVersion                  = "2.9.3"
val monixVersion                    = "2.3.2"
val catsVersion                     = "1.0.1"
val circeVersion                    = "0.9.0"
val scalaTestVersion                = "3.0.4"
val shapelessVersion                = "2.3.3"
val journalVersion                  = "3.0.19"
val akkaVersion                     = "2.5.9"
val akkaPersistenceInMemVersion     = "2.5.1.1"
val akkaPersistenceCassandraVersion = "0.55"
val akkaHttpVersion                 = "10.0.11"
val akkaHttpCirceVersion            = "1.19.0"
val elasticSearchVersion            = "6.1.2"
val log4jVersion                    = "2.10.0"
val commonsIOVersion                = "1.3.2"

lazy val catsCore           = "org.typelevel"                   %% "cats-core"                           % catsVersion
lazy val circeCore          = "io.circe"                        %% "circe-core"                          % circeVersion
lazy val circeParser        = "io.circe"                        %% "circe-parser"                        % circeVersion
lazy val circeGenericExtras = "io.circe"                        %% "circe-generic-extras"                % circeVersion
lazy val circeJava8         = "io.circe"                        %% "circe-java8"                         % circeVersion
lazy val scalaTest          = "org.scalatest"                   %% "scalatest"                           % scalaTestVersion
lazy val shapeless          = "com.chuusai"                     %% "shapeless"                           % shapelessVersion
lazy val monixEval          = "io.monix"                        %% "monix-eval"                          % monixVersion
lazy val journal            = "io.verizon.journal"              %% "core"                                % journalVersion
lazy val metricsCore        = "io.dropwizard.metrics"           % "metrics-core"                         % metricsCoreVersion
lazy val cassandraLauncher  = "com.typesafe.akka"               %% "akka-persistence-cassandra-launcher" % akkaPersistenceCassandraVersion
lazy val wesoSchema         = "com.github.bogdanromanx.es.weso" %% "schema"                              % wesoValidatorVersion
lazy val jenaArq            = "org.apache.jena"                 % "jena-arq"                             % jenaVersion
lazy val blazegraph         = "com.blazegraph"                  % "blazegraph-jar"                       % blazegraphVersion
lazy val jacksonAnnotations = "com.fasterxml.jackson.core"      % "jackson-annotations"                  % jacksonVersion
lazy val jacksonCore        = "com.fasterxml.jackson.core"      % "jackson-core"                         % jacksonVersion
lazy val jacksonDatabind    = "com.fasterxml.jackson.core"      % "jackson-databind"                     % jacksonVersion

lazy val akkaActor           = "com.typesafe.akka" %% "akka-actor"            % akkaVersion
lazy val akkaTestKit         = "com.typesafe.akka" %% "akka-testkit"          % akkaVersion
lazy val akkaClusterSharding = "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion
lazy val akkaDistributedData = "com.typesafe.akka" %% "akka-distributed-data" % akkaVersion
lazy val akkaStream          = "com.typesafe.akka" %% "akka-stream"           % akkaVersion

lazy val akkaPersistence          = "com.typesafe.akka"   %% "akka-persistence"           % akkaVersion
lazy val akkaPersistenceQuery     = "com.typesafe.akka"   %% "akka-persistence-query"     % akkaVersion
lazy val akkaPersistenceCassandra = "com.typesafe.akka"   %% "akka-persistence-cassandra" % akkaPersistenceCassandraVersion
lazy val akkaPersistenceInMem     = "com.github.dnvriend" %% "akka-persistence-inmemory"  % akkaPersistenceInMemVersion

lazy val akkaHttp        = "com.typesafe.akka" %% "akka-http"         % akkaHttpVersion
lazy val akkaHttpTestKit = "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion
lazy val akkaHttpCirce   = "de.heikoseeberger" %% "akka-http-circe"   % akkaHttpCirceVersion

lazy val log4jCore         = "org.apache.logging.log4j"          % "log4j-core"                % log4jVersion
lazy val log4jApi          = "org.apache.logging.log4j"          % "log4j-api"                 % log4jVersion
lazy val esCore            = "org.elasticsearch"                 % "elasticsearch"             % elasticSearchVersion
lazy val esPainless        = "org.codelibs.elasticsearch.module" % "lang-painless"             % elasticSearchVersion
lazy val esReindex         = "org.codelibs.elasticsearch.module" % "reindex"                   % elasticSearchVersion
lazy val esRestClient      = "org.elasticsearch.client"          % "elasticsearch-rest-client" % elasticSearchVersion
lazy val esTransportClient = "org.elasticsearch.plugin"          % "transport-netty4-client"   % elasticSearchVersion
lazy val commonsIO         = "org.apache.commons"                % "commons-io"                % commonsIOVersion

lazy val kamonCore       = "io.kamon" %% "kamon-core"            % "1.1.3"
lazy val kamonPrometheus = "io.kamon" %% "kamon-prometheus"      % "1.1.1"
lazy val kamonJaeger     = "io.kamon" %% "kamon-jaeger"          % "1.0.2"
lazy val kamonLogback    = "io.kamon" %% "kamon-logback"         % "1.0.3"
lazy val kamonMetrics    = "io.kamon" %% "kamon-system-metrics"  % "1.0.0"
lazy val kamonAkka       = "io.kamon" %% "kamon-akka-2.5"        % "1.1.2"
lazy val kamonAkkaHttp   = "io.kamon" %% "kamon-akka-http-2.5"   % "1.1.0"
lazy val kamonAkkaRemote = "io.kamon" %% "kamon-akka-remote-2.5" % "1.1.0"

lazy val types = project
  .in(file("modules/types"))
  .settings(
    name                := "commons-types",
    moduleName          := "commons-types",
    libraryDependencies ++= Seq(catsCore, circeCore, scalaTest % Test)
  )

lazy val sourcing = project
  .in(file("modules/sourcing/core"))
  .dependsOn(types)
  .settings(
    name                := "sourcing-core",
    moduleName          := "sourcing-core",
    libraryDependencies ++= Seq(catsCore, scalaTest % Test)
  )

lazy val sourcingAkka = project
  .in(file("modules/sourcing/akka"))
  .dependsOn(sourcing % "compile->compile;test->test")
  .settings(
    name       := "sourcing-akka",
    moduleName := "sourcing-akka",
    libraryDependencies ++= Seq(
      shapeless,
      akkaPersistence,
      akkaPersistenceQuery,
      akkaClusterSharding,
      akkaTestKit          % Test,
      akkaPersistenceInMem % Test,
      scalaTest            % Test
    )
  )

lazy val sourcingMem = project
  .in(file("modules/sourcing/mem"))
  .dependsOn(sourcing % "compile->compile;test->test")
  .settings(name := "sourcing-mem", moduleName := "sourcing-mem", libraryDependencies ++= Seq(scalaTest % Test))

lazy val service = project
  .in(file("modules/service"))
  .dependsOn(types, http, sourcingAkka % "test->compile")
  .settings(
    name       := "commons-service",
    moduleName := "commons-service",
    libraryDependencies ++= Seq(
      shapeless,
      akkaActor,
      akkaDistributedData,
      akkaHttp,
      akkaPersistenceCassandra,
      circeCore,
      circeParser,
      monixEval,
      journal,
      metricsCore        % Test,
      cassandraLauncher  % Test,
      akkaTestKit        % Test,
      akkaHttpTestKit    % Test,
      circeGenericExtras % Test,
      scalaTest          % Test
    )
  )

lazy val test = project
  .in(file("modules/test"))
  .dependsOn(types)
  .settings(
    name                := "commons-test",
    moduleName          := "commons-test",
    coverageEnabled     := false,
    libraryDependencies ++= Seq(circeCore, circeParser)
  )

lazy val http = project
  .in(file("modules/http"))
  .dependsOn(types, test % Test)
  .settings(
    name                := "commons-http",
    moduleName          := "commons-http",
    libraryDependencies ++= Seq(shapeless, akkaHttp, akkaHttpCirce, journal, scalaTest % Test, akkaHttpTestKit % Test, circeGenericExtras % Test)
  )

lazy val kamon = project
  .in(file("modules/kamon"))
  .settings(
    name       := "commons-kamon",
    moduleName := "commons-kamon",
    libraryDependencies ++= Seq(
      kamonCore,
      kamonPrometheus,
      kamonJaeger,
      kamonLogback,
      kamonMetrics,
      kamonAkka % Runtime,
      kamonAkkaHttp,
      kamonAkkaRemote % Runtime,
      akkaHttpTestKit % Test,
      scalaTest       % Test
    )
  )

lazy val iam = project
  .in(file("modules/iam"))
  .dependsOn(http, test)
  .settings(
    name                := "iam",
    moduleName          := "iam",
    libraryDependencies ++= Seq(akkaHttpCirce, circeGenericExtras, circeParser, circeJava8, akkaTestKit % Test, scalaTest % Test)
  )

lazy val queryTypes = project
  .in(file("modules/query-types"))
  .settings(
    name                := "commons-query-types",
    moduleName          := "commons-query-types",
    libraryDependencies ++= Seq(catsCore, circeCore, scalaTest % Test, circeGenericExtras % Test)
  )

lazy val elasticServerEmbed = project
  .in(file("modules/elastic-server-embed"))
  .dependsOn(test)
  .settings(
    name       := "elastic-server-embed",
    moduleName := "elastic-server-embed",
    libraryDependencies ++= Seq(
      akkaHttp,
      akkaStream,
      akkaTestKit,
      commonsIO,
      esCore,
      esPainless,
      esReindex,
      esRestClient,
      esTransportClient,
      log4jCore,
      log4jApi,
      scalaTest
    )
  )

lazy val elasticClient = project
  .in(file("modules/elastic-client"))
  .dependsOn(http, queryTypes, test % Test, elasticServerEmbed % Test)
  .settings(
    name       := "elastic-client",
    moduleName := "elastic-client",
    libraryDependencies ++= Seq(
      akkaStream,
      circeCore,
      circeParser        % Test,
      circeGenericExtras % Test
    )
  )

lazy val sparqlClient = project
  .in(file("modules/sparql-client"))
  .dependsOn(http, queryTypes)
  .settings(
    name       := "sparql-client",
    moduleName := "sparql-client",
    libraryDependencies ++= Seq(
      akkaStream,
      jenaArq,
      circeCore,
      circeParser        % Test,
      blazegraph         % Test,
      jacksonAnnotations % Test,
      jacksonCore        % Test,
      jacksonDatabind    % Test,
      akkaTestKit        % Test,
      scalaTest          % Test
    )
  )

lazy val forwardClient = project
  .in(file("modules/forward-client"))
  .dependsOn(http, queryTypes, test % Test)
  .settings(
    name       := "forward-client",
    moduleName := "forward-client",
    organization := "hbp.kg.nexus",
    version    := "1.0.0",
    libraryDependencies ++= Seq(
      akkaStream,
      circeCore,
      circeParser        % Test,
      circeGenericExtras % Test
    )
  )

lazy val shaclValidator = project
  .in(file("modules/ld/shacl-validator"))
  .dependsOn(types)
  .settings(
    name                := "shacl-validator",
    moduleName          := "shacl-validator",
    resolvers           += Resolver.bintrayRepo("bogdanromanx", "maven"),
    libraryDependencies ++= Seq(journal, wesoSchema, catsCore, circeCore, circeParser % Test, scalaTest % Test)
  )

lazy val schemas = project
  .in(file("modules/schemas"))
  .settings(
    name       := "commons-schemas",
    moduleName := "commons-schemas"
  )

lazy val root = project
  .in(file("."))
  .settings(name := "commons", moduleName := "commons")
  .settings(noPublish)
  .aggregate(types,
             sourcing,
             sourcingAkka,
             sourcingMem,
             http,
             test,
             kamon,
             service,
             queryTypes,
             elasticServerEmbed,
             elasticClient,
             sparqlClient,
             forwardClient,
             shaclValidator,
             iam,
             schemas)

lazy val noPublish = Seq(
  publishLocal    := {},
  publish         := {},
  publishArtifact := false
)

inThisBuild(
  List(
    homepage := Some(url("https://github.com/BlueBrain/nexus-commons")),
    licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    scmInfo  := Some(ScmInfo(url("https://github.com/BlueBrain/nexus-service"), "scm:git:git@github.com:BlueBrain/nexus-commons.git")),
    developers := List(
      Developer("bogdanromanx", "Bogdan Roman", "noreply@epfl.ch", url("https://bluebrain.epfl.ch/")),
      Developer("hygt", "Henry Genet", "noreply@epfl.ch", url("https://bluebrain.epfl.ch/")),
      Developer("umbreak", "Didac Montero Mendez", "noreply@epfl.ch", url("https://bluebrain.epfl.ch/")),
      Developer("wwajerowicz", "Wojtek Wajerowicz", "noreply@epfl.ch", url("https://bluebrain.epfl.ch/")),
      Developer("rdiana", "Remi Diana", "remi.diana@gmail.com", url("https://bluebrain.epfl.ch/"))
    ),
    // These are the sbt-release-early settings to configure
    releaseEarlyWith              := BintrayPublisher,
    releaseEarlyNoGpg             := true,
    releaseEarlyEnableSyncToMaven := false
  )
)

addCommandAlias("review", ";clean;scalafmtSbtCheck;coverage;scapegoat;test;coverageReport;coverageAggregate")
