import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

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
    maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
}
val ktor_version = "1.2.4"
val retrofit_version = "2.6.1"
val kotlinx_serialization_converter = "0.4.0"
val serialization_version = "0.12.0"

kotlin {
    js("frontend") {
        browser {}
    }
    jvm("retrofit")

    sourceSets {
        fun KotlinDependencyHandler.applyShared() {
            implementation(project(":shared"))
        }

        val commonMain by getting {
            dependencies {
                applyShared()
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization_version")
            }
        }
        val commonTest by getting {
            dependencies {
                applyShared()
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val `frontendMain` by getting {
            dependencies {
                applyShared()
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serialization_version")
            }
        }
        val `frontendTest` by getting {
            dependencies {
                applyShared()
                implementation(kotlin("test-js"))
            }
        }
        val `retrofitMain` by getting {
            dependencies {
                applyShared()
                implementation(kotlin("stdlib"))
                implementation("com.squareup.retrofit2:retrofit:2.6.1")
                implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.4.0")
            }
        }
    }
}
