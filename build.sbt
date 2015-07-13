name := "parallelai.spyglass"

organization := "com.vimeo"

enablePlugins(GitVersioning, GitBranchPrompt)
git.baseVersion := "4.4"
git.uncommittedSignifier := {
    val df = new java.text.SimpleDateFormat("yyyyMMdd'T'HHmmss")
    df setTimeZone java.util.TimeZone.getTimeZone("GMT")
    val suffix = (df format (new java.util.Date)) + "-MODIFIED-SNAPSHOT"
    if (git.gitHeadCommit.value.isDefined) {
      //println(git.gitHeadCommit.value.get)
      Some(suffix)
    } else {
      Some("INITIAL" + suffix)
    }
  }

scalaVersion := "2.11.5"

scalacOptions ++= Seq("-feature")

javacOptions in Compile ++= Seq("-source", "1.7", "-target", "1.7")

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Concurrent Maven Repo" at "http://conjars.org/repo",
  "Cloudera CDH4" at "https://repository.cloudera.com/artifactory/cloudera-repos",
  "Scalaz Bin Tray" at "http://dl.bintray.com/scalaz/releases",
  "Twitter Maven Repo" at "http://maven.twttr.com")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % "2.11.4" % "compile",
  "com.twitter" % "scalding-core_2.11" % "0.13.0" % "compile",
  "org.apache.hadoop" % "hadoop-core" % "2.6.0-mr1-cdh5.4.3" % "compile",
  "org.apache.hadoop" % "hadoop-common" % "2.6.0-cdh5.4.3" % "compile",
  "org.apache.hbase" % "hbase-client" % "1.0.0-cdh5.4.3" % "compile",
  "org.apache.hbase" % "hbase-common" % "1.0.0-cdh5.4.3" % "compile",
  "org.apache.hbase" % "hbase-server" % "1.0.0-cdh5.4.3" % "compile",
  "org.slf4j" % "slf4j-api" % "1.7.2" % "compile",
  "com.typesafe" % "config" % "1.0.0" % "compile",
  "com.twitter.elephantbird" % "elephant-bird-core" % "4.1" % "compile",
  "com.twitter.elephantbird" % "elephant-bird-hadoop-compat" % "4.1" % "compile",
  "joda-time" % "joda-time" % "2.7",
  "org.specs2" % "specs2_2.11" % "3.1.1" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test")

isSnapshot := {
    val bVersion = (version in ThisBuild).value
    println(bVersion)
    val regexVersion = """^(v\d+)\.(\d+)\.(\d+)$""".r
    bVersion match {
      case regexVersion(major, minor, patch) => false
      case _ => true
    }
  }

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo := {
    val nexus = "http://nyvimeomaven1.vimeows.com:8081/nexus/content/repositories/"
    if (isSnapshot.value) {
      Some("snapshots" at nexus + "snapshots")
    } else {
      Some("internal" at nexus + "internal")
    }
  }
