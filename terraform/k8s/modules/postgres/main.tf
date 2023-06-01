resource "helm_release" "postgres" {
  name = "postgres"

  repository = "oci://registry-1.docker.io/"
  chart      = "bitnamicharts/postgresql"
  version    = var.chart_version

  namespace        = "postgres"
  create_namespace = true

  set {
    name  = "global.postgresql.auth.postgresqlUsername"
    value = var.db_user
  }

  set {
    name  = "global.postgresql.auth.postgresqlPassword"
    value = var.db_password
  }

  set {
    name = "primary.initdb.scripts.init\\.sql"
    value = <<EOF
CREATE USER morpheus WITH PASSWORD 'findneo';
CREATE DATABASE "sre-lab";
GRANT CONNECT ON DATABASE "sre-lab" TO morpheus WITH GRANT OPTION;
GRANT CREATE ON DATABASE "sre-lab" TO morpheus WITH GRANT OPTION;
ALTER ROLE morpheus WITH CREATEROLE;
EOF
  }
}
