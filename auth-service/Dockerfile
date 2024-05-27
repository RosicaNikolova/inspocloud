# Use an OpenJDK 17 base image from Alpine for a lightweight container
FROM openjdk:17-jdk-alpine

# Expose the port the application uses
EXPOSE 8084

# Set the working directory inside the Docker image
WORKDIR /app
# Copy the application's built jar file to the image filesystem

# Copy the built jar file from your build/libs directory to the container
COPY build/libs/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar

ENTRYPOINT ["java", "-jar", "auth-service.jar"]

