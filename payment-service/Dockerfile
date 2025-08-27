# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mkdir -p /app/logs
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9090
ENV JAVA_OPTS="-Duser.timezone=Europe/Berlin"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
