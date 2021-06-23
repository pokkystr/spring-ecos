FROM adoptopenjdk:11-jre-openj9

ARG JAR_FILE=./target/mygcppubplanet*.jar

VOLUME /tmp

RUN mkdir -p /home

ADD ${JAR_FILE} /home/app.jar

WORKDIR /home

EXPOSE 8080
#ENTRYPOINT exec java -jar -Xms128m -Xmx256m  /home/app.jar
ENTRYPOINT exec java -jar $JAVA_OPTS -Dlogback.timezone=Asia/Bangkok /home/app.jar --spring.config.location=/home/application.yaml