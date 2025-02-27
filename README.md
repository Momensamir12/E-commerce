# E-commerce Microservices
This project is a demonstration of a microservices architecture for an e-commerce platform

Project Overview
This project consists of four main services, each responsible for a specific domain within the e-commerce platform:

Product Catalog Service

Manages products and inventory.
Allows public access to view products, while only admins can add, remove, or update products.
Reduces product inventory upon order completion.
Database: MongoDB
Order Service

Handles order placement and completion.
Sends events to notify other services upon order placement.
Database: PostgreSQL
Payment Service

Integrates with Stripe to handle payments for orders.
Designed to support additional payment providers in the future.
Database: PostgreSQL
Cart Service

Manages adding products to the cart for later checkout.
Database: Redis
Key Features
Microservices Architecture: Each service is independently deployable and scalable.
Spring Boot: All services are built using Spring Boot for rapid development and production-ready applications.
Security: Services are secured as OAuth2 resource servers with Keycloak as the authorization server.
Saga Pattern: Implemented using the Axon Framework to handle rollbacks on failures or complete orders.
Event-Driven Communication: Utilizes Kafka as an event bus for asynchronous communication between services.
Deployment: Services are containerized and can be deployed on a local Kubernetes cluster or using Docker Compose for quick local testing.
Getting Started
Prerequisites
Java 11 or higher
Docker and Docker Compose
Kubernetes (for local cluster deployment)
MongoDB, PostgreSQL, and Redis instances
Installation
Clone the Repository

BASH

git clone https://github.com/yourusername/ecommerce-microservices.git
cd ecommerce-microservices
Set Up Environment Variables

Configure your environment variables for database connections, Stripe API keys, and Keycloak settings.

Build and Run with Docker Compose

BASH

docker-compose up --build
Deploy on Kubernetes

Ensure your Kubernetes cluster is running and apply the configurations:

BASH

kubectl apply -f k8s/
Usage
Access the services via their respective endpoints.
Use Keycloak for authentication and authorization.
Monitor events and logs to understand the flow of operations and communication between services.
Future Enhancements
Add support for additional payment providers.
Implement a user service for managing customer accounts.
Enhance monitoring and logging with tools like Prometheus and Grafana.
Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any improvements or bug fixes.

License
This project is licensed under the MIT License.

Contact
For any questions or feedback, please contact me at [your-email@example.com].

