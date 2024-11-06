plugins {
	kotlin("jvm") version "1.9.25"
	application
}

group = "com.caju"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

application {
	mainModule = ":application"
	mainClass = "com.caju.PaymentAuthorizerApplicationKt"
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
