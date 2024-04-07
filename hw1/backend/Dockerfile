# Use official Maven image with Java 17 installed
FROM maven:3.8.4-openjdk-17-slim AS build

# Set working directory
WORKDIR /app

# Copy the Maven project
COPY pom.xml .

# Copy the source code
COPY src ./src

# Run the application
CMD ["mvn", "spring-boot:run" ]
