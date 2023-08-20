resource "helm_release" "orders-api" {
  name = "orders-api"

  chart     = "../../k8s/charts/orders-api"
  namespace = var.namespace
  timeout = 60
}
