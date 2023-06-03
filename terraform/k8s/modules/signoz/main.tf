resource "helm_release" "signoz" {
  name = "signoz"

  repository        = "https://charts.signoz.io"
  chart             = "signoz"
  namespace         = var.namespace
  dependency_update = true
}
