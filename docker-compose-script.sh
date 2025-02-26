#!/bin/bash

# First start infrastructure
cd docker
echo "Starting infrastructure services..."
#docker network create app-network
for service in keycloak axon kafka; do
    echo "Starting $service..."
    docker compose -f ./$service/docker-compose.yml up -d
done

# Wait for infrastructure
echo "Waiting for infrastructure to be ready..."
sleep 120

# Start databases
echo "Starting databases..."
for db in postgresql mongodb redis; do
    echo "Starting $db..."
    docker compose -f ./$db/docker-compose.yml up -d
done

# Wait for databases
echo "Waiting for databases to be ready..."
sleep 30

# Start application services
echo "Starting application services..."
docker compose -f ./services/docker-compose.yml up -d

echo "All services have been started"