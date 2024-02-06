FROM openjdk:8-jdk-alpine

COPY ./target/crud-0.0.5-SNAPSHOT.jar ./crud.jar

EXPOSE 8080

CMD ["java", "-jar", "/crud.jar"]
