FROM 192.168.5.155/micro-service-demo/openjdk:8-jre
MAINTAINER Yuan

COPY target/api-getway-zuul-1.0-SNAPSHOT.jar /api-getway-zuul.jar

ENTRYPOINT ["java", "-jar", "api-getway-zuul.jar"]