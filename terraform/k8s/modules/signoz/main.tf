module "signoz_namespace" {
  source = "../kubernetes-namespace"
  name   = var.namespace
}

resource "helm_release" "signoz" {
  name = "signoz"

  depends_on = [ module.signoz_namespace ]
  repository        = "https://charts.signoz.io"
  chart             = "signoz"
  namespace         = var.namespace
  dependency_update = true
}
