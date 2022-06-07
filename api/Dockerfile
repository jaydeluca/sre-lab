FROM gradle:7.4-jdk11 as BUILD

COPY ./api/. /src
WORKDIR /src
RUN gradle build -x test --no-daemon

FROM openjdk:11-jre

COPY --from=BUILD /src/build/libs/sre-lab-1.0-SNAPSHOT.jar /bin/runner/main.jar
WORKDIR /bin/runner
RUN wget -O opentelemetry-javaagent-all.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.14.0/opentelemetry-javaagent.jar

CMD ["java", "-javaagent:opentelemetry-javaagent-all.jar", "-jar", "main.jar"]
