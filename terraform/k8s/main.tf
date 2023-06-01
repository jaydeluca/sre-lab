module "postgres" {
  source = "./modules/postgres"
}

module "orders-migrations" {
  source = "./modules/orders-migrations"
}


module "orders-api" {
  source = "./modules/orders-api"
}
