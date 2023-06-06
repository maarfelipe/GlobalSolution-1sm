FROM openjdk:19-jdk-alpine
VOLUME /main-app
ADD target/cropsage-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app.jar" ]