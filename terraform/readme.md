# Terraform

The cluster will be setup via helm charts that are applied via terraform

- Create postgres db
- Run migrations on the db to setup schema
- Deploy orders api
- Deploy users api
- Deploy signoz
- Deploy a load generator


```
kubectl --namespace postgres port-forward $(k get pods -n postgres | grep postgres | awk '{print $1}') 5432:5432

# Jump into db
kubectl run postgresql-client --rm --tty -i --restart='Never' --namespace postgres --image bitnami/postgresql:latest --env="PGPASSWORD=findneo" --command -- psql --host postgres-postgresql -U morpheus -d sre-lab -p 5432
```