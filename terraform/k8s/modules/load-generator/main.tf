resource "helm_release" "load-generator" {
  name = "load-generator"

  chart     = "../../k8s/charts/load-generator"
  namespace = var.namespace
}
