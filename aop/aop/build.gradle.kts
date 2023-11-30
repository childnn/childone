plugins {
    id("java")
}

group = "org.example.aop"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // spring-aop
    implementation("org.springframework:spring-aop:6.0.10")
    // aspectj
    implementation("org.aspectj:aspectjweaver:1.9.19")
    // aopalliance
    implementation("aopalliance:aopalliance:1.0")
    // objenesis
    implementation("org.objenesis:objenesis:3.3")
}

tasks.test {
    useJUnitPlatform()
}