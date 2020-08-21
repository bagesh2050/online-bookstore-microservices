# Create an Image
FROM openjdk:8-jre-alpine
VOLUME /tmp

WORKDIR /opt/application/order
ADD target/*.jar app.jar
EXPOSE 7004
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/opt/application/order/app.jar"]