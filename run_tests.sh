#!/bin/bash

# Set the working directory to the project root
cd /app

# Run Maven tests without downloading dependencies or connecting to the internet
mvn clean test

# Print the test results
echo "Test execution completed."