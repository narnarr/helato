buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-mysql:10.14.0")
    }
}

plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id("org.flywaydb.flyway") version "10.0.0"
}

group = "dev.nars"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")
}

flyway {
    baselineOnMigrate = true
    cleanDisabled = false
    url = "jdbc:mysql://localhost:3306"
    user = "root"
    schemas = arrayOf("HELATO")
}
