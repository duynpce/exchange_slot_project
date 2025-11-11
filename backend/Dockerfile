# Stage 1: Build .jar từ source
FROM openjdk:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper và pom.xml
COPY mvnw pom.xml ./
COPY .mvn/ .mvn
RUN chmod +x mvnw

# Copy source code
COPY src ./src

# Build package, skip test để nhanh hơn
RUN ./mvnw clean package -DskipTests

# Stage 2: Chạy ứng dụng
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy .jar từ stage build
COPY --from=build /app/target/API-Testing.jar app.jar

# Expose port Spring Boot mặc định
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
