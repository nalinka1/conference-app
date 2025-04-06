FROM openjdk:20-jdk
COPY target/conference-app.jar /app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar", "server", "config.yml"]