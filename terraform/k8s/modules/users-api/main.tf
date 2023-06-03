resource "helm_release" "users-api" {
  name = "users-api"

  chart     = "../../k8s/charts/users-api"
  namespace = var.namespace
}
