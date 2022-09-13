#!/bin/bash

docker build -f ./users-api/Dockerfile -t users-api:latest .
docker build -f ./api/Dockerfile -t orders-api:latest .


kubectl apply -f k8s/users-api.yml