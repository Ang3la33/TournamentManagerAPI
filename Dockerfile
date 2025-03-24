# Use a lightweight JDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar file (update the name if needed)
COPY target/TournamentManagerAPI-1.0-SNAPSHOT.jar app.jar

# Expose port Spring Boot runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

