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

locals {
  virtual_service_manifest =  <<EOT
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: signoz-ingress-istio
  namespace: ${var.namespace}
spec:
  hosts:
  - "*"
  gateways:
  - signoz-gateway
  http:
  - route:
    - destination:
      host: "signoz-frontend.${var.namespace}.svc.cluster.local"
      port:
        number: 3301
EOT

  gateway_manifest = <<EOT
apiVersion: networking.istio.io/v1alpha3
kind: Gateway
metadata:
  name: signoz-gateway
  namespace: ${var.namespace}
spec:
  selector:
    istio: ingressgateway
  servers:
  - port:
      number: 80
      name: http
      protocol: HTTP
    hosts:
    - "*"
EOT

}

resource "kubernetes_manifest" "istio_gateway" {
  manifest = yamldecode(local.gateway_manifest)
}

resource "kubernetes_manifest" "istio_virtual_service" {
  manifest = yamldecode(local.virtual_service_manifest)
}