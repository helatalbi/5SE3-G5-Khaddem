# Use an OpenJDK Runtime as a parent image
FROM openjdk:19
# Define environment variables
# Set the working directory to /app
WORKDIR /app
# Copy the executable into the container at /app
ADD target/*.jar app.jar
# Make port 8080 available to the world outside this container
EXPOSE 8089
# Run app.jar when the container launches
CMD ["java", "-jar", "/app/app.jar"]