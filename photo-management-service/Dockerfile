# Expose the port the application uses
EXPOSE 8081

# Set the working directory inside the Docker image
WORKDIR /app

# Copy the built jar file from your build/libs directory to the container
# Ensure your Gradle build names the jar file consistently, or adjust this line to match the output name
COPY photo-management-service/build/libs/photo-management-service-0.0.1-SNAPSHOT.jar photo-management-service.jar

# Define the command to run your app using Java
# If your application listens on a different port, ensure to update the EXPOSE command above accordingly
ENTRYPOINT ["java", "-jar", "photo-management-service.jar"]
