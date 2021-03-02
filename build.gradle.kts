// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("https://www.jitpack.io")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.objectBoxGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.hiltGradlePlugin)
        classpath(BuildPlugins.navigationGradlePlugin)
        classpath(BuildPlugins.gmsGradlePlugin)
        classpath(BuildPlugins.firebasePerfGradlePlugin)
        classpath(BuildPlugins.firebaseCrashlyticsGradlePlugin)
        classpath(BuildPlugins.easyLauncherGradlePlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        flatDir {
            dirs = setOf(File("libs"))
        }
        google()
        jcenter()
        mavenCentral()
        maven("https://www.jitpack.io")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven { url = uri("https://dl.bintray.com/arrow-kt/arrow-kt/") }
        maven { url = uri("https://dl.bintray.com/objectbox/objectbox/") }
    }
}

tasks.register("cleanBuild").configure {
    delete("build")
}