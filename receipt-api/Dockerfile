# Use an OpenJDK base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/receipt-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
