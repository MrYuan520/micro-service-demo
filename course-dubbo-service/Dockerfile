FROM 192.168.5.155/micro-service-demo/openjdk:8-jre
MAINTAINER Yuan

COPY target/course-dubbo-service-1.0-SNAPSHOT.jar /course-dubbo-service.jar

ENTRYPOINT ["java", "-jar", "course-dubbo-service.jar"]