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
}
