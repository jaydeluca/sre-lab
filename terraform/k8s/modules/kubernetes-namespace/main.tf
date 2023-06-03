resource "kubernetes_namespace" "namespace" {
  metadata {
    annotations = {
      name = var.name
    }

    labels = {
      "app" = var.name
    }

    name = var.name
  }
}

output "output_name" {
  value = kubernetes_namespace.namespace.metadata[0].name
}