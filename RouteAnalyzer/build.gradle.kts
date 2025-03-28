plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.10"
    application
    id("com.gradleup.shadow") version "8.3.5"
}

group = "it.polito.wa2.g20"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.uber:h3:4.1.1")
    implementation("org.yaml:snakeyaml:2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("org.yaml:snakeyaml:2.0")
    testImplementation("io.mockk:mockk:1.13.17")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("it.polito.wa2.g20.MainKt")
}