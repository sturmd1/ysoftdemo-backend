FROM openjdk:8
COPY /target/ysoft-demo-backend-1.0-SNAPSHOT.jar /usr/src/myapp/backend.jar
WORKDIR /usr/src/myapp
CMD ["java" ,"-jar", "backend.jar"]
