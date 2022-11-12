plugins {
    id("fabric-loom") version "0.12-SNAPSHOT"
    kotlin("jvm") version "1.7.0"
}
fun p(key: String): Any? = extra.get(key)

version = "1.0.0"
group = "shateq.fabric"
base.archivesName.set("disconnect-keybind-fabric-${p("mc")}")

dependencies {
    mappings(loom.officialMojangMappings())
    minecraft("com.mojang:minecraft:${p("mc")}")
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

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    processResources {
        charset("UTF-8") // Must have!
        // Token replacing
        filesMatching("fabric.mod.json") {
            expand("version" to version)
        }
    }
}
