# Start with a base image with OpenJDK installed
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
#WORKDIR /app

# Copy the built JAR file from Jenkins workspace to the container
COPY target/Foyer-0.0.1-SNAPSHOT.jar /app/Foyer.jar

# Expose port 8080 for the Spring Boot application
#EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/Foyer.jar"]


