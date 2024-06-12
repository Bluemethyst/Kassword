val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.24"
    id("io.ktor.plugin") version "2.3.11"
}

group = "dev.bluemethyst.web"
version = "0.0.2"

application {
    mainClass.set("dev.bluemethyst.kassword.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    //implementation("io.ktor:ktor-server-core-jvm")
    //implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-apache:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.google.code.gson:gson:2.11.0")
    //testImplementation("io.ktor:ktor-server-tests-jvm")
    //testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

ktor {
    fatJar {
        archiveFileName.set("fat.jar")
    }
}

sourceSets {
    main {
        resources {
            srcDir("resources")
        }
        kotlin {
            srcDir("kotlin")
        }
    }
}
