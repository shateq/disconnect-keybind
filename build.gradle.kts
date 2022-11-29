plugins {
    id("fabric-loom") version "0.12-SNAPSHOT"
    id("com.modrinth.minotaur") version "2.+"
    kotlin("jvm") version "1.7.0"
}
fun p(key: String): String = project.extra.get(key) as String

version = "1.1.1"
group = "shateq.mods" //no maven publish
base.archivesName.set("disconnect-keybind-$version-fabric-${p("mc")}")
description = "Bind yourself a button to Disconnect!"

dependencies {
    minecraft("com.mojang:minecraft:${p("mc")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${p("loader")}")

    modImplementation("net.fabricmc:fabric-language-kotlin:${p("kotlin_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${p("fapi_version")}")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withSourcesJar()
}

tasks {
    jar {
        from("LICENSE") {
            rename { "$it-${rootProject.name}" }
        }
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    processResources {
        filteringCharset = "UTF-8"
        filesMatching("fabric.mod.json") {
            expand(
                "version" to version,
                "description" to description
            )
        }
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN")) // This is the default. Remember to have the MODRINTH_TOKEN environment variable set or else this will fail, or set it to whatever you want - just make sure it stays private!
    projectId.set("disconnect")
    versionNumber.set(version.toString())
    versionType.set("release")

    uploadFile.set(tasks.remapJar.name)
    gameVersions.addAll("1.19", "1.19.1", "1.19.2")
    dependencies {
        // scope.type : can be `required`, `optional`, `incompatible`, or `embedded`
        required.project("fabric-api")
        required.project("fabric-language-kotlin")
    }
}
