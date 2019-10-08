FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY src/main/resources/Application.yml /config/application.yml
COPY build/libs/*.jar app.jar
CMD ["java","-jar","/app.jar"]
