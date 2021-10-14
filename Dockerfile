FROM adoptopenjdk/openjdk11:alpine-jre
RUN apk --update add redis
ARG JARFILE=dolab-configserver-workshop/build/libs/*.jar
COPY ${JARFILE} app.jar
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh