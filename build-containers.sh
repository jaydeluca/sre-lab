#!/bin/bash
kubectl config use-context docker-desktop

docker build -f ./users-api/Dockerfile -t users-api:latest .
docker build -f ./api/Dockerfile -t orders-api:latest .