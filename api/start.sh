#!/usr/bin/env bash

if [[ -z "${CUSTOM_JAVA_AGENT}" ]]; then
  AGENT=""
else
  AGENT="-javaagent:$CUSTOM_JAVA_AGENT"
fi

java "$AGENT" -jar main.jar server "$CONFIG_ENVIRONMENT".yml