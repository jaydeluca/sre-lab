#!/bin/bash
kubectl config use-context docker-desktop

# Database
kubectl apply -f k8s/postgres-config.yaml
kubectl apply -f k8s/postgres-pvc-pv.yaml
kubectl apply -f k8s/postgres-deployment.yaml
kubectl apply -f k8s/postgres-service.yaml

# SigNoz
DEFAULT_STORAGE_CLASS=$(kubectl get storageclass -o=jsonpath='{.items[?(@.metadata.annotations.storageclass\.kubernetes\.io/is-default-class=="true")].metadata.name}')

kubectl patch storageclass "$DEFAULT_STORAGE_CLASS" -p '{"allowVolumeExpansion": true}'

helm repo add signoz https://charts.signoz.io
kubectl create ns platform
helm --namespace platform install signoz signoz/signoz

POD_NAME=$(kubectl get pods --namespace platform -l "app.kubernetes.io/name=signoz,app.kubernetes.io/instance=signoz,app.kubernetes.io/component=frontend" -o jsonpath="{.items[0].metadata.name}")

# Apps
kubectl apply -f k8s/users-api.yml
kubectl apply -f k8s/orders-api.yml

kubectl --namespace platform port-forward "$POD_NAME" 3301:3301