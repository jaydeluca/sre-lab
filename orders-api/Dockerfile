FROM gradle:7.5-jdk17 as BUILD

COPY ./orders-api/. /app
WORKDIR /app

RUN gradle shadowJar -x test --no-daemon

RUN wget -O elastic-apm-agent.jar https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.31.0/elastic-apm-agent-1.31.0.jar
RUN wget -O opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar

FROM amazoncorretto:17

COPY --from=BUILD /app/elastic-apm-agent.jar /bin/runner/elastic-apm-agent.jar
COPY --from=BUILD /app/opentelemetry-javaagent.jar /bin/runner/opentelemetry-javaagent.jar
COPY --from=BUILD /app/build/libs/sre-lab-1.0-SNAPSHOT-all.jar /bin/runner/main.jar
COPY --from=BUILD /app/src/main/dev.yml /bin/runner/dev.yml
COPY --from=BUILD /app/src/main/k8s.yml /bin/runner/k8s.yml
COPY --from=BUILD /app/start.sh /bin/runner/start.sh

WORKDIR /bin/runner

CMD ["./start.sh"]
