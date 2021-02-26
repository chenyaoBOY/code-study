FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD /target/spring-boot-kong-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Xmx250m", "-jar","/app.jar"]
