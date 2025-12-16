FROM maven:3.9.9-eclipse-temurin-23 AS builder
WORKDIR /app
COPY . /app
RUN mvn package -DskipTests

FROM eclipse-temurin:23-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "./app.jar"]