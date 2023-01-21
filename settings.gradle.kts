pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
    }
    plugins {
        id("fabric-loom") version "1.0-SNAPSHOT"
        id("com.modrinth.minotaur") version "2.+"
        kotlin("jvm") version "1.8.0"
    }
}
rootProject.name = "disconnectkeybind"
