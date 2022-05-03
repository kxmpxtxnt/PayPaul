plugins {
    id("de.nycode.spigot-dependency-loader") version "1.1.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    java
}

dependencies {
    implementation("de.chojo:sql-util:1.2.3")
    implementation("de.eldoria:messageblocker:1.1.1")
    implementation("de.eldoria:eldo-util:1.13.5")
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    spigot("net.kyori:adventure-api:4.10.1")
    spigot("net.kyori:adventure-text-minimessage:4.10.1")
    spigot("com.zaxxer:HikariCP:5.0.1")
}

repositories {
    mavenCentral()
    maven("https://eldonexus.de/repository/maven-public")
    maven("https://chojonexus.de/repository/maven-public")
    maven("https://papermc.io/repo/repository/maven-public/")
}

group = "me.kxmpxtxnt"
version = "1.0-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
