pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
    }
    plugins {
        id("fabric-loom") version "1.7-SNAPSHOT"
        id("com.modrinth.minotaur") version "2.+"
        kotlin("jvm") version "2.0.10"
    }
}
rootProject.name = "disconnectkeybind"
