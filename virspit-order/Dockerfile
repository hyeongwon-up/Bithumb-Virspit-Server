FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=build/libs/*.jar
ENV USE_PROFILE stage
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]