locals {
  istio_namespace = "istio-system"
  istio_ingress_namespace = "istio-ingress"
}

module "istio_system_namespace" {
  source = "../kubernetes-namespace"
  name   = local.istio_namespace
}


module "istio_ingress_namespace" {
  source = "../kubernetes-namespace"
  name   = local.istio_ingress_namespace
}


resource "helm_release" "istio" {
  name = "istio-base"

depends_on = [ module.istio_system_namespace ]
  repository        = "https://istio-release.storage.googleapis.com/charts"
  chart             = "base"
  namespace         = local.istio_namespace
  dependency_update = true
}


resource "helm_release" "istio-base" {
  name = "istio-base"

depends_on = [ module.istio_system_namespace ]
  repository        = "https://istio-release.storage.googleapis.com/charts"
  chart             = "base"
  namespace         = local.istio_namespace
  dependency_update = true
}

resource "helm_release" "istiod" {
  name = "istiod"

depends_on = [ module.istio_system_namespace ]
  repository        = "https://istio-release.storage.googleapis.com/charts"
  chart             = "istiod"
  namespace         = local.istio_namespace
  dependency_update = true
}

resource "helm_release" "istio-ingress" {
  name = "istio-ingress"

depends_on = [ module.istio_ingress_namespace, resource.helm_release.istiod ]
  repository        = "https://istio-release.storage.googleapis.com/charts"
  chart             = "gateway"
  namespace         = local.istio_ingress_namespace
  dependency_update = true
}


