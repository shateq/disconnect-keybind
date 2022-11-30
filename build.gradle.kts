plugins {
    id("fabric-loom") version "1.0-SNAPSHOT"
    id("com.modrinth.minotaur") version "2.+"
    //id("com.matthewprenger.cursegradle") version "1.4.0"
    kotlin("jvm") version "1.7.0"
}
fun p(key: String): String = project.extra.get(key) as String

version = "1.2.0"
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
            expand("version" to version)
        }
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN")) // This is the default. Remember to have the MODRINTH_TOKEN environment variable set or else this will fail, or set it to whatever you want - just make sure it stays private!
    projectId.set("disconnect")
    versionName.set("Disconnect Keybind $version for MC ${p("mc")}")
    versionNumber.set(version.toString())
    versionType.set("release")

    uploadFile.set(tasks["remapJar"])
    gameVersions.addAll("1.19.3-pre2", "1.19.3-pre3")
    dependencies {
        // scope.type : can be `required`, `optional`, `incompatible`, or `embedded`
        required.project("fabric-api")
        required.project("fabric-language-kotlin")
    }
}

/*TODO is failing
curseforge {
    apiKey = System.getenv("CURSE")
    options(closureOf<Options> {
        forgeGradleIntegration = false
        javaIntegration = true
    })
    project(closureOf<CurseProject> {
        id = "disconnect"
        releaseType = "release"
        changelog = "Support 1.19.3-pre3"

        relations(closureOf<CurseRelation> {
            requiredDependency("fabric-api")
            requiredDependency("fabric-language-kotlin")
        })

        addGameVersion("Fabric")
        addGameVersion("1.19.3")
        addGameVersion("Java 17")
        mainArtifact(tasks.remapJar.get().archiveFile.get().asFile)
        afterEvaluate {
            uploadTask.dependsOn(tasks.remapJar)
        }
    })
}*/
