plugins {
    id("java")
    id("jacoco")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.5.3")
    // https://mvnrepository.com/artifact/commons-io/commons-io

    // https://mvnrepository.com/artifact/com.github.freva/ascii-table
//    implementation("com.github.freva:ascii-table:1.8.0")
// https://mvnrepository.com/artifact/de.vandermeer/asciitable
    implementation("de.vandermeer:asciitable:0.3.2")
    // https://mvnrepository.com/artifact/org.jacoco/org.jacoco.agent
    testImplementation("org.jacoco:org.jacoco.agent:0.8.8")
// https://mvnrepository.com/artifact/junit/junit
    testImplementation("junit:junit:4.13.2")


}
tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()

}