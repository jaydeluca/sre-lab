locals {
  config_context = "docker-desktop"
}

provider "kubernetes" {
  config_path    = "~/.kube/config"
  config_context = local.config_context
}


terraform {
  backend "kubernetes" {
    config_path      = "~/.kube/config"
    config_context   = "docker-desktop"
    load_config_file = true
    secret_suffix    = "state"
    version          = ">= 2.21.0"
  }
}

provider "helm" {
  kubernetes {
    config_context = local.config_context
    config_path    = "~/.kube/config"
  }
}