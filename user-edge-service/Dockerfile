FROM 192.168.5.155/micro-service-demo/openjdk:8-jre
MAINTAINER Yuan

COPY target/user-edge-service-1.0-SNAPSHOT.jar /user-edge-service.jar

ENTRYPOINT ["java", "-jar", "user-edge-service.jar"]