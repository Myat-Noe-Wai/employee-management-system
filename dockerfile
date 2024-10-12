# Use the official OpenJDK image as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar to the working directory
COPY /home/user/Spring/springboot-react-mydemo/target/springboot-react-mydemo-0.0.1-SNAPSHOT.jar

# Expose the port that your Spring Boot app runs on
EXPOSE 8080

# Set environment variable for the PORT Render will provide
ENV PORT 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
