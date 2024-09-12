# Use the official OpenJDK image as a parent image
FROM openjdk:8-jdk-alpine

# Install Maven
RUN apk add --no-cache curl tar bash
ARG MAVEN_VERSION=3.8.8
ARG USER_HOME_DIR="/root"
RUN mkdir -p /usr/share/maven && \
curl -fsSL https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Copy the project source
COPY src ./src

# Package the application
RUN mvn package -DskipTests

# Run the jar file 
ENTRYPOINT ["java","-jar","/app/target/social_recommend-0.0.1-SNAPSHOT.jar"]
