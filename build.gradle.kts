plugins {
	id("fabric-loom") version "0.12-SNAPSHOT"
	kotlin("jvm") version "1.7.0"
	// id("maven-publish")
}

version = "1.0"
group = "shateq.fabric"
val minecraft = "1.19"

repositories {}

dependencies {
	minecraft("com.mojang:minecraft:$minecraft")
	mappings(loom.officialMojangMappings())
	modImplementation("net.fabricmc:fabric-loader:0.14.8")

	modImplementation("net.fabricmc:fabric-language-kotlin:1.8.1+kotlin.1.7.0")
	modImplementation("net.fabricmc.fabric-api:fabric-api:0.57.0+1.19")
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
	withSourcesJar()
}

tasks {
	jar {
		from("LICENSE") {
			rename { "${it}_DisconnectKeybind"}
		}
	}

	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions.jvmTarget = "17"
	}

	processResources {
		filteringCharset = "UTF-8" // Must have!
		// Token replacing
		filesMatching("fabric.mod.json") {
			expand("version" to version)
		}
	}
}

/*publishing {
	publications {
		mavenJava(MavenPublication) {
			from components.java
		}
	}

	repositories {}
}*/