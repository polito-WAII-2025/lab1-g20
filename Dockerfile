FROM openjdk:17-jdk-slim

WORKDIR /app

COPY RouteAnalyzer-1.0-SNAPSHOT-all.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
