FROM adoptopenjdk/openjdk11:alpine-jre
ARG JARFILE=build/libs/*.jar
COPY ${JARFILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]