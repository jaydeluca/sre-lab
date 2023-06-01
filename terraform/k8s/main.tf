module "postgres" {
  source = "./modules/postgres"
}

module "orders-migrations" {
  source = "./modules/orders-migrations"
}
