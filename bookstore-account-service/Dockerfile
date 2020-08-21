# Create an Image
FROM openjdk:8-jre-alpine
VOLUME /tmp

WORKDIR /opt/application/account
ADD target/*.jar app.jar
EXPOSE 7001
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/opt/application/account/app.jar"]