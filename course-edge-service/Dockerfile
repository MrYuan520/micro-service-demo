FROM 192.168.5.155/micro-service-demo/openjdk:8-jre
MAINTAINER Yuan

COPY target/course-edge-service-1.0-SNAPSHOT.jar /course-edge-service.jar

ENTRYPOINT ["java", "-jar", "course-edge-service.jar"]