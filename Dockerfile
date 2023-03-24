FROM openjdk:8
EXPOSE 5000
ADD target/spring-boot-security-jwt-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]