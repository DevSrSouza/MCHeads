buildscript {
    repositories {
        jcenter()
    }
}

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.3.50"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.3.50"
}
repositories {
    jcenter()
    maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
    mavenCentral()
}
val ktor_version = "1.2.4"
val logback_version = "1.2.3"
val serialization_version = "0.12.0"
val h2_version = "1.0.60"
val hikaricp_version = "3.3.1"
val exposed_version = "0.16.1"

kotlin {
    jvm("backend")
    //jvm("retrofit-client")
    js {
        browser {}
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val backendMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("io.ktor:ktor-server-netty:$ktor_version")
                implementation("io.ktor:ktor-locations:$ktor_version")
                implementation("io.ktor:ktor-serialization:$ktor_version")
                implementation("ch.qos.logback:logback-classic:$logback_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialization_version")
                implementation("org.jetbrains.exposed:exposed:$exposed_version")
                implementation("com.zaxxer:HikariCP:$hikaricp_version")
                implementation("com.h2database:h2:$h2_version")
            }
        }
        val backendTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serialization_version")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
