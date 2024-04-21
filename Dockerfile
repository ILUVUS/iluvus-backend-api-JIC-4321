# Use an official Maven image as the base image
FROM maven:3.9.6-eclipse-temurin-17 AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src

ARG MONGODB_URI
ARG MONGODB_DB

# RUN touch /app/src/main/resources/application.properties

# RUN echo "spring.data.mongodb.uri=${MONGODB_URI}" >> /app/src/main/resources/application.properties \
# && echo "spring.data.mongodb.database=${MONGODB_DB}" >> /app/src/main/resources/application.properties \
# && echo "iluvus.email.passwordtoken=${PROJECT_PASSWORDTOKEN}" >> /app/src/main/resources/application.properties

# Build the application using Maven
RUN mvn clean package -DskipTests
# Use an official OpenJDK image as the base image
FROM eclipse-temurin:latest
# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY --from=build /app/target/backend-*.jar ./backend.jar
# Set the command to run the application
CMD ["java", "-jar", "backend.jar"]