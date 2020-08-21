# Create an Image
FROM openjdk:8-jre-alpine
VOLUME /tmp

WORKDIR /opt/application/inventory
ADD target/*.jar app.jar
EXPOSE 7002
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/opt/application/inventory/app.jar"]