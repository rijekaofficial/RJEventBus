plugins {
    kotlin("jvm") version "2.3.21"
    `maven-publish`
}

group = "dev.rijeka.rjeventbus"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

java {
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])

            groupId = "dev.rijeka.rjeventbus"
            artifactId = "rjeventbus"
            version = "1.0.0"
        }
    }

    repositories {
        maven {
            name = "GitHubPages"
            url = uri(layout.buildDirectory.dir("maven-repo"))
        }
    }
}