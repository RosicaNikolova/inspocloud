plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.inspocloud"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    //Security
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
//    implementation("org.springframework.boot:spring-boot-starter-security")

    //Rabbit
    implementation ("org.springframework.boot:spring-boot-starter-amqp")

    //Mongo
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


}

tasks.withType<Test> {
    useJUnitPlatform()
}
