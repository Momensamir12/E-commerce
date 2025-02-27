directories=(
    "k8s/istio"
    "k8s/axon"
    "k8s/kafka"
    "k8s/keycloak"
    "k8s/mongodb"
    "k8s/postgresql"
    "k8s/redis"
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


# Get the external IP of the load balancer
LOAD_BALANCER_IP=$(kubectl get svc keycloak -o jsonpath='{.status.loadBalancer.ingress[0].ip}')

ISTIO_GATEWAY_IP=$(kubectl get svc istio-ingressgateway -n istio-system -o jsonpath='{.status.loadBalancer.ingress[0].ip}')

# Update a ConfigMap with the IP
kubectl create configmap istio-gateway-ip --from-literal=GATEWAY_IP=$ISTIO_GATEWAY_IP --dry-run=client -o yaml | kubectl apply -f -

# Construct the issuer URL
ISSUER_URL="http://$LOAD_BALANCER_IP:8080/auth/realms/e-commerce"
JWKS_URL="http://$LOAD_BALANCER_IP:8080/auth/realms/e-commerce/protocol/openid-connect/certs"


# Update a ConfigMap with the issuer URL
kubectl create configmap issuer-url --from-literal=ISSUER_URL=$ISSUER_URL --dry-run=client -o yaml | kubectl apply -f -
kubectl create configmap jwks-url --from-literal=JWKS_URL=$JWKS_URL --dry-run=client -o yaml | kubectl apply -f -
kubectl patch requestauthentication jwt-auth --type='json' -p='[{"op": "replace", "path": "/spec/jwtRules/0/issuer", "value":"'"$ISSUER_URL"'"}, {"op": "replace", "path": "/spec/jwtRules/0/jwksUri", "value":"'"$JWKS_URL"'"}]'
