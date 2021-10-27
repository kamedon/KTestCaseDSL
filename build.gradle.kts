import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("multiplatform") version "1.5.31"
    id("maven-publish")
    java
    jacoco
}

jacoco {
    toolVersion = "0.8.7"
}


repositories {
    mavenCentral()
}

group = "com.kamedon"
version = "0.4.3"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js(LEGACY) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }


    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }

}

publishing {

    val credentials = loadProperties("$rootDir/github.properties")

    repositories {
        maven {
            name = "KTestCaseDSL"
            url = uri("https://maven.pkg.github.com/kamedon/KTestCaseDSL")
            credentials {
                username = credentials.getProperty("user")
                password = credentials.getProperty("password")
            }
        }
    }

}
fun loadProperties(fileName: String): Properties {
    val props = Properties()
    val file = file(fileName)
    if (!file.exists()) {
        props.setProperty("user", System.getenv("GITHUB_ACTOR"))
        props.setProperty("password", System.getenv("GITHUB_TOKEN"))
        return props
    }
    props.load(file.inputStream())
    return props
}

tasks.jacocoTestReport {
    val coverageSourceDirs = arrayOf(
        "src/commonMain",
        "src/jvmMain"
    )

    val classFiles = File("${buildDir}/classes/kotlin/jvm/")
        .walkBottomUp()
        .toSet()

    classDirectories.setFrom(classFiles)
    sourceDirectories.setFrom(files(coverageSourceDirs))

    executionData
        .setFrom(files("${buildDir}/jacoco/jvmTest.exec"))
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}