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

SIGNOZ_FRONTEND_POD=$(kubectl get pods --namespace platform -l "app.kubernetes.io/name=signoz,app.kubernetes.io/instance=signoz,app.kubernetes.io/component=frontend" -o jsonpath="{.items[0].metadata.name}")

# Build apps if docker images do not exist
if [[ "$(docker images -q users-api:latest 2> /dev/null)" == "" ]] \
  || [[ "$(docker images -q orders-api:latest 2> /dev/null)" == "" ]]; then
  echo "[INFO] App docker images do not exist ~> Building docker images through ./build-containers.sh"
  sh ./build-containers.sh
fi

kubectl apply -f k8s/users-api.yml
kubectl apply -f k8s/orders-api.yml

echo "[INFO] Waiting for signoz pod $SIGNOZ_FRONTEND_POD to be in ready state -> Port forwarding"
kubectl -n platform wait pod --for=condition=Ready "$SIGNOZ_FRONTEND_POD" --timeout=300s
kubectl --namespace platform port-forward "$SIGNOZ_FRONTEND_POD" 3301:3301