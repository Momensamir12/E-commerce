#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Define the image names and paths to Dockerfiles and source code
declare -A services
services=(
  ["cart-service"]="cart-service"
  ["order-service"]="order-service"
  ["payment-service"]="payment-service"
  ["product-catalog-service"]="product-catalog-service"


  # Add more services as needed
)
#cd "common" && ./mvnw clean install -DskipTests
#cd ..
# Function to build the JAR file
build_jar() {
  local service_path=$1
  echo "Building JAR for $service_path..."

  # Navigate to the service directory and build the JAR
  (cd "$service_path" && ./mvnw clean package -DskipTests)

  echo "JAR for $service_path built successfully."
}

# Loop through each service, build the JAR, and then build the Docker image
for service in "${!services[@]}"; do
  path=${services[$service]}

  # Build the JAR file
  build_jar "$path"

  echo "Building Docker image for $service..."

  # Build the Docker image
  docker build -t "$service:latest" "$path"

  echo "Docker image for $service built successfully."

#  kind load docker-image "$service:latest"
done

echo "All images built successfully."

