plugins {
    id("fabric-loom")
    id("com.modrinth.minotaur")
    kotlin("jvm")
}
fun p(key: String): String = properties[key] as String

version = "1.2.1"
group = "shateq.mods"
base.archivesName.set("disconnect-keybind-fabric-${p("mc")}")
description = "Bind yourself a button to disconnect!"

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

        val props = mapOf(
            "version" to version
        )
        inputs.properties(props)

        filesMatching("fabric.mod.json") {
            expand(props)
        }
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN")) // This is the default. Remember to have the MODRINTH_TOKEN environment variable set or else this will fail, or set it to whatever you want - just make sure it stays private!
    projectId.set("disconnect")
    versionName.set("Disconnect Keybind $version for 1.20")
    versionNumber.set("mc${p("mc")}-$version")
    versionType.set("release")

    uploadFile.set(tasks["remapJar"])
    gameVersions.addAll("1.20", "1.20.1")
    dependencies {
        // scope.type : can be `required`, `optional`, `incompatible`, or `embedded`
        required.project("fabric-api")
        required.project("fabric-language-kotlin")
    }
}
