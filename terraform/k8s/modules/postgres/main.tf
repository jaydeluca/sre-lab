resource "helm_release" "postgres" {
  name = "postgres"

  repository = "oci://registry-1.docker.io/"
  chart      = "bitnamicharts/postgresql"
  version    = var.chart_version

  namespace        = "postgres"
  create_namespace = true

  set {
    name  = "primary.initdb.scripts.init\\.sql"
    value = <<EOF
CREATE USER ${var.db_user} WITH PASSWORD '${var.db_password}';
GRANT ALL ON SCHEMA public TO ${var.db_user};
EOF
  }
} 
