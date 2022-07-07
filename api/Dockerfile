FROM gradle:7.4-jdk17 as BUILD

COPY ./api/. /src
WORKDIR /src

RUN wget -O apm.jar https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.31.0/elastic-apm-agent-1.31.0.jar

RUN gradle build -x test --no-daemon

FROM openjdk:17-jdk

COPY --from=BUILD /src/apm.jar /bin/runner/apm.jar
COPY --from=BUILD /src/build/libs/sre-lab.jar /bin/runner/main.jar
WORKDIR /bin/runner

CMD ["java", "-javaagent:apm.jar", "-jar", "main.jar", "server"]
