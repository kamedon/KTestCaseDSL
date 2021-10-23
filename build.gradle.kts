import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("multiplatform") version "1.5.31"
    id("maven-publish")
}


repositories {
    mavenCentral()
}

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

group = "com.kamedon"
version = "0.2.0"

publishing {

    val credentials = loadProperties("$rootDir/github.properties")
    repositories {
        maven {
            name = "KTestCaseDSL"
            url = uri("https://maven.pkg.github.com/kamedon/KTestCaseDSL")
//            CREDENTIALS
            credentials {
                username = credentials.getProperty("user")
                password = credentials.getProperty("password")
            }
        }
    }

}
fun loadProperties(fileName: String): Properties {
    val props = Properties()
    props.load(file(fileName).inputStream())
    return props
}
