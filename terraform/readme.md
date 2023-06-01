# Terraform

The cluster will be setup via helm charts that are applied via terraform


```
ubectl --namespace postgres port-forward $(k get pods -n postgres | grep postgres | awk '{print $1}') 5432:5432

# Jump into db
export POSTGRES_PASSWORD=$(k get secret -n postgres postgres-postgresql -o jsonpath="{.data.postgres-password}" | base64 --decode)


kubectl run postgresql-client --rm --tty -i --restart='Never' --namespace postgres --image bitnami/postgresql:latest --env="PGPASSWORD=$POSTGRESQL_PASSWORD" --command -- psql --host postgres-postgresql -U postgres -d postgres -p 5432

```