FROM openjdk:21-jdk

COPY build/libs/*SNAPSHOT-plain.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app-jar"]
