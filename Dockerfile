# Use the latest Ubuntu image as the base
FROM ubuntu:latest

# Set environment variables for non-interactive installs
ENV DEBIAN_FRONTEND=noninteractive

# Install necessary packages including Git, Maven, and JDK
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

# Set the working directory in the container to /app
WORKDIR /app

# Copy the entire project directory (including .git) into the container
COPY . .

# Resolve Maven dependencies, compile the project, skip unit tests during build to speed up the process
RUN mvn clean install -DskipTests

# The port that the application uses, expose it
EXPOSE 8080

# Command to run the application using Java
CMD ["java", "-jar", "target/social_recommend-0.0.1-SNAPSHOT.jar"]

WORKDIR /app
# Copy the run_tests.sh script to the root of the container
COPY run_tests.sh /run_tests.sh

# Make the script executable
RUN chmod +x /run_tests.sh
ENTRYPOINT ["/bin/bash", "-s"]