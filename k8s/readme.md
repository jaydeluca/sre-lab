# K8s deployment of lab

Start a cluster

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

k apply -f k8s/users-api.yml

k apply -f k8s/orders-api.yml

k exec -it $(k get pods | grep postgres | awk '{print $1}') --  psql -h localhost -U morpheus --password -p 5432 sre-lab

# PSQL, check schema
\c sre-lab
\dt

# Port forward Signoz
kubectl --namespace platform port-forward "$POD_NAME" 3301:3301

k delete deploy orders-api
```