FROM openjdk:17-windowsservercore
EXPOSE 8080
ADD target/projFixed-0.0.1-SNAPSHOT.jar spring-app
ENTRYPOINT ["java","-jar","/spring-app"]
