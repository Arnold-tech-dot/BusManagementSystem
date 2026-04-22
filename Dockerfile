# Use official OpenJDK runtime as base image
FROM openjdk:21-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY BusManagementSystem.jar app.jar

# Expose no specific port (console app)
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
