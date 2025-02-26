directories=(
    "k8s/axon"
    "k8s/kafka"
    "k8s/keycloak"
    "k8s/mongodb"
    "k8s/postgresql"
    "k8s/redis"
    "k8s/istio"
)

# Loop through each directory
for dir in "${directories[@]}"; do
    echo "Applying manifests in directory: $dir"

    # Check if the directory exists
    if [ -d "$dir" ]; then
        # Apply all YAML files in the directory
        kubectl apply -f "$dir"
    else
        echo "Directory $dir does not exist. Skipping."
    fi
done