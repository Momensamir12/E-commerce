# Start with a base image containing Java runtime
# Start with a base image containing Java runtime
FROM openjdk:23

# Add Maintainer Info
LABEL maintainer="momen.samir.eb@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

EXPOSE 2025

# The application's jar file
ARG JAR_FILE=target/product-catalog-service-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]