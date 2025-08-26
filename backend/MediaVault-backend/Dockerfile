# Build stage
FROM maven AS build
WORKDIR /app/
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21
WORKDIR /app/
COPY --from=build /app/target/MediaVault-backend-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "MediaVault-backend-0.0.1-SNAPSHOT.jar"]
