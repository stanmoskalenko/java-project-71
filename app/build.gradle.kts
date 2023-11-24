import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    application
    checkstyle
    jacoco
    id("com.adarshr.test-logger") version "4.0.0"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("hexlet.code.App")
}

tasks.compileJava {
    version = 20
}

dependencies {
    implementation("info.picocli:picocli:4.7.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.16.0")

    // Test deps
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")
}

tasks.test {
    useJUnitPlatform()
    reports.enabled
    testLogging.exceptionFormat = TestExceptionFormat.FULL
    testlogger {
        setTheme(ThemeType.MOCHA)
        showSummary = true
        showSkipped = true
        showFailed = true
        showPassed = true
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
    }
}
