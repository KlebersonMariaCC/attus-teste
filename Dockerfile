#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAVA_OPTS
#ENV JAVA_OPTS=$JAVA_OPTS
#COPY target/teste03-0.0.1-SNAPSHOT.jar attusteste.jar
#EXPOSE 3000
##ENTRYPOINT exec java $JAVA_OPTS -jar attusteste.jar
## For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar attusteste.jar

FROM maven:3.9.6-eclipse-temurin-17-focal

WORKDIR /app

ENV APP_TARGET target
ENV APP teste03-0.0.1-SNAPSHOT.jar

COPY teste03/pom.xml ./

COPY teste03/src ./src 

RUN mvn install -DskipTests -Dspring.profiles.active=dev

EXPOSE 8080

ENTRYPOINT exec java -jar /app/target/${APP}
