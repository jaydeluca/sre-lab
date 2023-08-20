locals {
  application_namespace = "production"
}

module "postgres" {
  source = "./modules/postgres"
}

module "application_namespace" {
  source = "./modules/kubernetes-namespace"
  name   = local.application_namespace
}

module "orders-migrations" {
  source     = "./modules/orders-migrations"
  namespace  = local.application_namespace
  depends_on = [module.application_namespace, module.postgres]
}

module "orders-api" {
  source     = "./modules/orders-api"
  namespace  = local.application_namespace
  depends_on = [module.application_namespace, module.orders-migrations]
}

module "users-api" {
  source    = "./modules/users-api"
  namespace = local.application_namespace
}


module "signoz" {
  source     = "./modules/signoz"
  namespace  = "platform"
}

module "load-generator" {
  depends_on = [module.orders-api, module.users-api, module.signoz]
  source     = "./modules/load-generator"
  namespace  = local.application_namespace
}

module "service-mesh" {
  source = "./modules/istio"  
}