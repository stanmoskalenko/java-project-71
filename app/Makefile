.PHONY: build

setup:
	./gradlew wrapper --gradle-version 8.4

run-dist:
	./build/install/app/bin/app

build:
	./gradlew clean build

install:
	./gradlew clean installDist

lint:
	./gradlew checkstyleMain

test:
	./gradlew test

check-deps:
	./gradlew dependencyUpdates -Drevision=release

report:
	./gradlew test jacocoTestReport
