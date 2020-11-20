import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.0" apply false
    id("io.spring.dependency-management") version "1.0.10.RELEASE" apply false
    kotlin("jvm") version "1.4.10" apply false
    kotlin("plugin.spring") version "1.4.10" apply false
    id("com.google.cloud.tools.jib") version "2.6.0" apply false
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
    group = "com.xunfos.budgetcat"
    version = "0.0.1"

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
}

subprojects {
    apply {
        plugin("com.google.cloud.tools.jib")
    }
}

