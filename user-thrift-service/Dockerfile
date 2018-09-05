FROM 192.168.5.155/micro-service-demo/openjdk:8-jre
MAINTAINER yuanbo

COPY target/user-thrift-service-1.0-SNAPSHOT.jar /user-service.jar

ENTRYPOINT ["java", "-jar", "user-service.jar"]