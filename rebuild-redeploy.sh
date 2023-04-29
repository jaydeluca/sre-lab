#!/bin/bash

./build-containers.sh

kubectl delete deploy users-api & kubectl apply -f k8s/users-api.yml
kubectl delete deploy orders-api & kubectl apply -f k8s/orders-api.yml

kubectl delete job load-test & kubectl apply -f k8s/load.yml