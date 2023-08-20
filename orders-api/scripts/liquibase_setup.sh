#!bin/bash

sh ./docker-entrypoint.sh --url=${POSTGRES_URL} --changeLogFile=${CHANGELOG_FILE} update