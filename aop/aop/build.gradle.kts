plugins {
    id("java")
    id("io.freefair.aspectj.post-compile-weaving") version "8.10" // 使用最新版本
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // 使用 JDK 17，或换成所需版本
    }
}


group = "org.example.aop"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // spring-context
    implementation("org.springframework:spring-context:6.0.10")
    // spring-aop
    implementation("org.springframework:spring-aop:6.0.10")
    // aspectj
    // org.aspectj:aspectjrt - the AspectJ runtime
    // org.aspectj:aspectjweaver - the AspectJ weaver
    // org.aspectj:aspectjtools - the AspectJ compiler
    // org.aspectj:aspectjmatcher - the AspectJ matcher
    //  include the aspectjweaver.jar to introduce advice to the Java class at load time:
    implementation("org.aspectj:aspectjweaver:1.9.19")
    // AspectJ runtime library aspectjrt.jar
    implementation("org.aspectj:aspectjrt:1.9.19")
    // aopalliance
    implementation("aopalliance:aopalliance:1.0")
    // objenesis
    implementation("org.objenesis:objenesis:3.3")

    // spring-aspects
    implementation("org.springframework:spring-aspects:6.0.10")
    // spring-instrument
    implementation("org.springframework:spring-instrument:6.0.10")

    // javax-annotation
    implementation("org.objenesis:objenesis:3.3")
}

tasks.test {
    useJUnitPlatform()
}