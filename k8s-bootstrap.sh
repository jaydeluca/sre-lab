#!/bin/bash
kubectl config use-context docker-desktop

# Add Charts
helm repo add signoz https://charts.signoz.io
helm repo add istio https://istio-release.storage.googleapis.com/charts
helm repo update

# Database
kubectl apply -f k8s/postgres-config.yaml
kubectl apply -f k8s/postgres-pvc-pv.yaml
kubectl apply -f k8s/postgres-deployment.yaml
kubectl apply -f k8s/postgres-service.yaml


DEFAULT_STORAGE_CLASS=$(kubectl get storageclass -o=jsonpath='{.items[?(@.metadata.annotations.storageclass\.kubernetes\.io/is-default-class=="true")].metadata.name}')

kubectl patch storageclass "$DEFAULT_STORAGE_CLASS" -p '{"allowVolumeExpansion": true}'

# ECK

# kubectl create -f https://download.elastic.co/downloads/eck/2.7.0/crds.yaml

# kubectl apply -f https://download.elastic.co/downloads/eck/2.7.0/operator.yaml

# kubectl apply -f k8s/elasticsearch.yml

# kubectl apply -f k8s/kibana.yml

# kubectl apply -f k8s/elastic-apm.yml


# SigNoz
kubectl create ns platform
helm --namespace platform install signoz signoz/signoz

SIGNOZ_FRONTEND_POD=$(kubectl get pods --namespace platform -l "app.kubernetes.io/name=signoz,app.kubernetes.io/instance=signoz,app.kubernetes.io/component=frontend" -o jsonpath="{.items[0].metadata.name}")

# Build apps if docker images do not exist
if [[ "$(docker images -q users-api:latest 2> /dev/null)" == "" ]] \
  || [[ "$(docker images -q orders-api:latest 2> /dev/null)" == "" ]]; then
  echo "[INFO] App docker images do not exist ~> Building docker images through ./build-containers.sh"
  sh ./build-containers.sh
fi

# Service Mesh
kubectl create namespace istio-system
helm install istio-base istio/base -n istio-system
helm install istiod istio/istiod -n istio-system --wait

kubectl label namespace default istio-injection=enabled --overwrite
istioctl install -f k8s/tracing.yml
kubectl apply -f k8s/envoy.yml

# Ingress Gateway
kubectl create namespace istio-ingress
kubectl label namespace istio-ingress istio-injection=enabled
helm install istio-ingress istio/gateway -n istio-ingress --wait

# Apps
kubectl apply -f k8s/orders-migrations.yaml
kubectl apply -f k8s/users-api.yml
kubectl apply -f k8s/orders-api.yml

# Kickoff a load test
kubectl delete job load-test & kubectl apply -f k8s/load.yml

# echo "[INFO] Waiting for kibana pod $SIGNOZ_FRONTEND_POD to be in ready state -> Port forwarding"
# kubectl -n platform wait pod --for=condition=Ready "$SIGNOZ_FRONTEND_POD" --timeout=300s
# kubectl port-forward service/quickstart-kb-http 5601


echo "[INFO] Waiting for signoz pod $SIGNOZ_FRONTEND_POD to be in ready state -> Port forwarding"
kubectl -n platform wait pod --for=condition=Ready "$SIGNOZ_FRONTEND_POD" --timeout=300s
kubectl --namespace platform port-forward "$SIGNOZ_FRONTEND_POD" 3301:3301

kubectl --namespace postgres port-forward $(k get pods -n postgres | grep postgres | awk '{print $1}') 5432:5432