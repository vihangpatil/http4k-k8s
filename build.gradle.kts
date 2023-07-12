import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "dev.vihang"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:_"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-ktorcio")
    implementation("org.http4k:http4k-cloudnative")
    runtimeOnly("ch.qos.logback:logback-classic:_")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_20.majorVersion
}

application {
    mainClass.set("dev.vihang.http4k.MainKt")
    applicationName = "backend"
    version = ""
    applicationDefaultJvmArgs = listOf("-Dlogback.configurationFile=logback.gcp.xml")
}