FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
COPY target/homeFlow-1.0.0.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
