# K8s deployment of lab

Start a cluster

Prerequisites:
- Helm
- Kubectl
- Istio
- Docker

Run from project root:
```
./k8s-bootstrap.sh
```


Useful Commands
```bash
k get services

k get pods

k get configmap

k get deployments

k apply -f k8s/postgres-config.yaml

k logs -f {pod} 

kubectl logs -f $(k get pods | grep users-api | awk '{print $1}')

kubectl logs -f $(k get pods | grep orders-api | awk '{print $1}')

kubectl logs -f $(k get pods | grep load-test | awk '{print $1}')

k apply -f k8s/users-api.yml

k apply -f k8s/orders-api.yml

# Jump into db
k exec -it $(k get pods | grep postgres | awk '{print $1}') --  psql -h localhost -U morpheus --password -p 5432 sre-lab

# PSQL, check schema
\c sre-lab
\dt

# Port forward Signoz
kubectl --namespace platform port-forward "$POD_NAME" 3301:3301


# Redeploy orders-api
k delete deploy orders-api & k apply -f k8s/orders-api.yml
```

Run Load test
```
k delete job load-test & k apply -f k8s/load.yml
```


