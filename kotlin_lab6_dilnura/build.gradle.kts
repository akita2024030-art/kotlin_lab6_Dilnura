plugins {
    kotlin("jvm") version "2.2.20"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(19)
}

application {
    mainClass.set("lab6.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
