# Stage 1: Build jar
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY mvnw pom.xml ./
COPY .mvn/ .mvn
RUN chmod +x mvnw
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jdk AS runtime
WORKDIR /app
COPY --from=build /app/target/API-Testing.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
