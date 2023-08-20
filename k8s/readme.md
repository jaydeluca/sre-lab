# K8s deployment of lab

Start a cluster

Prerequisites:
- Helm
- Kubectl
- Istio
- Docker
- Terraform


Terraform
```
cd terraform/k8s
terraform apply
```


This will spin up the services, a load test, and SigNoz where you can view whats happening

## Accessing

```
kubectl --namespace platform port-forward $(kubectl get pods --namespace platform -l "app.kubernetes.io/name=signoz,app.kubernetes.io/instance=signoz,app.kubernetes.io/component=frontend" -o jsonpath="{.items[0].metadata.name}") 3301:3301
```

http://localhost:3301

Create a new account


## Troubleshooting


Useful Commands
```bash
k get services

k get pods

k get configmap

k get deployments

k apply -f k8s/postgres-config.yaml

k logs -f {pod} 

kubectl logs -f $(k get pods | grep users-api | awk '{print $1}')

kubectl logs -f $(k get pods | grep orders-migrations | awk '{print $1}')

kubectl logs -f $(k get pods | grep Running | grep orders-api | awk '{print $1}')

kubectl logs -n production -f $(k get pods -n production | grep load-generator | awk '{print $1}')

kubectl logs -f $(k get pods | grep quickstart-kb | awk '{print $1}')

k exec -it $(k get pods | grep dnsutil | awk '{print $1}') --  bash

k apply -f k8s/users-api.yml

k apply -f k8s/orders-api.yml

# Jump into db
k exec -it $(k get pods | grep postgres | awk '{print $1}') --  psql -h localhost -U morpheus --password -p 5432 sre-lab

# PSQL, check schema
\c sre-lab      # jump into database
\dt             # list all tables
\d orders       # schema for table

# Port forward Signoz
kubectl --namespace platform port-forward $(kubectl get pods --namespace platform -l "app.kubernetes.io/name=signoz,app.kubernetes.io/instance=signoz,app.kubernetes.io/component=frontend" -o jsonpath="{.items[0].metadata.name}") 3301:3301


# Redeploy orders-api
k delete deploy orders-api & k apply -f k8s/orders-api.yml
```

Run Load test
```
k delete job load-test & k apply -f k8s/load.yml
```


The first time you run elastic you will need to get the password:

`kubectl get secret quickstart-es-elastic-user -o go-template='{{.data.elastic | base64decode}}'`

https://0.0.0.0:5601/

username: elastic

