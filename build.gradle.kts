plugins {
    java
    idea
    id("fabric-loom") version "1.6-SNAPSHOT"
}

group = "tech.execsuroot"
version = "1.0.0"

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

base {
    archivesName = "cosmo"
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    minecraft("com.mojang:minecraft:1.20.1")
    mappings("net.fabricmc:yarn:1.20.1+build.1:v2")
    modImplementation("net.fabricmc:fabric-loader:0.15.11")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.83.0+1.20.1")
    modImplementation(files("libs/LuckPerms-Fabric-5.4.102.jar"))
    modImplementation("me.lucko:fabric-permissions-api:0.2-SNAPSHOT")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}