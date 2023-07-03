module "nats_namespace" {
  source          = "../../../modules/kube_namespace/1.0.0"
  namespace       = "nats"
  namespace_label = "nats.jetsream.k8s"
}

module "nats_helm" {
  source = "../../../modules/generic_helm_release/1.0.0"

  chart_name    = var.chart_name
  repository    = var.repository
  chart         = var.chart
  chart_version = var.chart_version

  namespace = var.namespace

  chart_value_overides = var.helm_overrides

}

output "helm_overrides" {
  value = var.helm_overrides
}
