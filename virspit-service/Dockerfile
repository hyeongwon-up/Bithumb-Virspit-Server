FROM openjdk:11-jre-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
ADD ${JAR_FILE} product-app.jar
EXPOSE 8080
ENTRYPOINT ["java",
"-Dspring.data.mongodb.uri=mongodb://192.168.219.104/test",
"-Djava.security.egd=file:/dev/./urandom",
"-jar",
"/app.jar"]