FROM openjdk:21-jdk-slim
LABEL authors="maksimignatov"

WORKDIR /app
COPY target/weather-app-t1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080/tcp
CMD ["java", "-XX:+UseG1GC", "-jar", "app.jar"]