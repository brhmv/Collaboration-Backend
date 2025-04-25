# Stage 1:
FROM gradle:7.5.1-jdk21 AS build

COPY . .

RUN gradle build -x test

# Stage 2:
FROM eclipse-temurin:21-jre-alpine

COPY build/libs/turing-collab-0.0.1.jar /turing-collab-0.0.1.jar

ENTRYPOINT ["java", "-jar", "/turing-collab-0.0.1.jar"]
