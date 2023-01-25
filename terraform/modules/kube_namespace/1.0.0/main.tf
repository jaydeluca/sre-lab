resource "kubernetes_namespace" "namespace" {
  metadata {
    labels = {
      mylabel = var.namespace_label
    }
    name = var.namespace
  }
}
