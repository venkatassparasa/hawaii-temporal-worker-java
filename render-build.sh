#!/bin/bash

# Render Build Script for Java Worker
echo "🚀 Building Java Temporal Worker for Render..."

# Install Maven if not present
if ! command -v mvn &> /dev/null; then
    echo "📦 Installing Maven..."
    apt-get update
    apt-get install -y maven
fi

# Set Java version
export JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"

echo "🔨 Building with Maven..."
mvn clean package -DskipTests

echo "✅ Build completed!"
echo "📦 JAR file created in target/ directory"

# List the built files
ls -la target/*.jar
