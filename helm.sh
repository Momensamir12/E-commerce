#!/bin/bash

cd k8s/helm
# List of directories containing Helm charts
directories=(
  "cart-service-chart"
  "order-service-chart"
  "payment-service-chart"
  "product-catalog-service-chart"
)

# Loop through each directory
for dir in "${directories[@]}"; do
    echo "Deploying Helm chart in directory: $dir"
    
    # Check if the directory exists
    if [ -d "$dir" ]; then
        # Extract the chart name from the directory name
        chart_name=$(basename "$dir")
        
        # Check if the release already exists
        if helm ls --all --short | grep -q "^$chart_name$"; then
            echo "Upgrading existing release: $chart_name"
            helm upgrade "$chart_name" "$dir"
        else
            echo "Installing new release: $chart_name"
            helm install "$chart_name" "$dir"
        fi
    else
        echo "Directory $dir does not exist. Skipping."
    fi
done