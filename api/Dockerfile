FROM gradle:jdk17 as BUILD


COPY ./api/. /app
WORKDIR /app

RUN wget -O apm.jar https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.31.0/elastic-apm-agent-1.31.0.jar

RUN gradle shadowJar -x test --no-daemon

FROM amazoncorretto:17

COPY --from=BUILD /app/apm.jar /bin/runner/apm.jar
COPY --from=BUILD /app/build/libs/sre-lab-1.0-SNAPSHOT-all.jar /bin/runner/main.jar
COPY --from=BUILD /app/src/main/dev.yml /bin/runner/config.yml
WORKDIR /bin/runner

CMD ["java", "-javaagent:apm.jar", "-jar", "main.jar", "server", "config.yml"]
