#!/bin/bash
kubectl config use-context docker-desktop

kubectl apply -f k8s/users-api.yml