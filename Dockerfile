# Use a base image with Java and Docker installed
FROM openjdk:19

# Copy the application JAR file to the container
COPY demo/target/Rat23.jar Rat23.jar

# Copy the Firebase service account JSON file to the container
COPY demo/ratatouille23-grp21-firebase-adminsdk-134t0-7755617655.json /demo/ratatouille23-grp21-firebase-adminsdk-134t0-7755617655.json

# Define the command to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "Rat23.jar"]
