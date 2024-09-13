# Use the latest Ubuntu image
FROM ubuntu:latest

# Set environment variables for non-interactive installs
ENV DEBIAN_FRONTEND=noninteractive

# Install dependencies
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    zip \
    curl \
    tar \
    wget \
    openjdk-11-jdk \
    maven

# Set JAVA_HOME environment variable for JDK
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:${PATH}"

# Set the working directory inside the container
WORKDIR /app

# Copy the project files to the container
COPY . .

# Install project dependencies and compile the project
RUN mvn clean compile

# Run the tests
RUN mvn test

# Build the package (creates the JAR file)
RUN mvn package -DskipTests

# Expose the port that the Spring Boot app runs on (default 8080)
EXPOSE 8080

# Set the entry point to run the JAR file generated in the target directory
CMD ["java", "-jar", "/app/target/social_recommend-0.0.1-SNAPSHOT.jar"]
