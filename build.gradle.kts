plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("org.springframework:spring-context:5.3.18")
    implementation("org.springframework:spring-core:5.3.18")
    implementation("org.springframework:spring-beans:5.3.18")
    implementation("org.springframework:spring-expression:5.3.18")
    implementation("cglib:cglib:3.3.0")
    implementation("org.springframework:spring-jdbc:5.3.18")

    testImplementation("junit:junit:4.13.1")
    testImplementation("org.springframework:spring-test:5.3.19")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
