#!/bin/bash
kubectl config use-context docker-desktop

kubectl apply -f k8s/postgres-config.yaml
kubectl apply -f k8s/postgres-pvc-pv.yaml
kubectl apply -f k8s/postgres-deployment.yaml

kubectl apply -f k8s/users-api.yml

kubectl apply -f k8s/orders-api.yml