#!/bin/bash
kubectl config use-context docker-desktop

CURRENT_DIRECTORY=$PWD

cd terraform/k8s

terraform apply -auto-approve

cd $CURRENT_DIRECTORY

echo "[INFO] Waiting for signoz pod $SIGNOZ_FRONTEND_POD to be in ready state -> Port forwarding"
# kubectl -n platform wait pod --for=condition=Ready "$SIGNOZ_FRONTEND_POD" --timeout=300s
kubectl --namespace platform port-forward $(kubectl get pods --namespace platform -l "app.kubernetes.io/name=signoz,app.kubernetes.io/instance=signoz,app.kubernetes.io/component=frontend" -o jsonpath="{.items[0].metadata.name}") 3301:3301

