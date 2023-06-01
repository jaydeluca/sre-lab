locals {
  config_context = "docker-desktop"
}

provider "kubernetes" {
  config_context   = local.config_context
  load_config_file = true
}


terraform {
  backend "kubernetes" {
    config_path    = "~/.kube/config"
    config_context = "docker-desktop"
    secret_suffix  = "state"
  }
}

provider "helm" {
  kubernetes {
    config_context = local.config_context
    config_path    = "~/.kube/config"
  }
}