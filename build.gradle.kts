import javafx.scene.control.Tooltip.install

plugins {
    kotlin("jvm") version "1.3.61"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    val ktorVersion = "1.3.0"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")

    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.0")
    testImplementation("org.slf4j:slf4j-simple:1.7.26")

    implementation("io.ktor:ktor-freemarker:$ktorVersion")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.8.+")


}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
