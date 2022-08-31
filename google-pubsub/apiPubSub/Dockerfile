FROM adoptopenjdk:11-jre-openj9

ARG JAR_FILE=./target/mygcppubplanet-0.0.1-SNAPSHOT.jar

VOLUME /tmp

RUN mkdir -p /home

ADD ${JAR_FILE} /home/app.jar

WORKDIR /home

EXPOSE 8080
ENTRYPOINT exec java -jar -Xms128m -Xmx256m  /home/app.jar