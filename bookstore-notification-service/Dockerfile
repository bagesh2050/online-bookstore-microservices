# Create an Image
FROM openjdk:8-jre-alpine
VOLUME /tmp

WORKDIR /opt/application/notification
ADD target/*.jar app.jar
EXPOSE 7003
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/opt/application/notification/app.jar"]