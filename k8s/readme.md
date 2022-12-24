# K8s deployment of lab

Start a cluster

Run from project root:
```
./k8s-bootstrap.sh
```


Useful Commands
```
k get services

k get pods

k get configmap

k get deployments

k apply -f k8s/postgres-config.yaml

k logs -f {pod} 

k logs -f $(k get pods | grep orders-api | awk '{print $1}')

k apply -f k8s/users-api.yml

k apply -f k8s/orders-api.yml

k exec -it [pod-name] --  psql -h localhost -U morpheus --password findneo -p 5432 postgresdb


k delete deploy orders-api
```