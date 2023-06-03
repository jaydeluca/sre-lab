resource "helm_release" "orders-migrations" {
  name = "orders-migrations"

  chart     = "../../k8s/charts/orders-migrations"
  namespace = var.namespace
}
