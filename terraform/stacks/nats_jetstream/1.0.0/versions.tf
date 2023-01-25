terraform {
  required_version = "1.2.6"

  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "2.17.0"
    }
    helm = {
      source  = "hashicorp/helm"
      version = "2.8.0"
    }
    jetstream = {
      source  = "nats-io/jetstream"
      version = "0.0.34"
    }
  }
}

provider "helm" {
  kubernetes {
    config_path = "~/.kube/config"
  }
}

provider "kubernetes" {
  config_path    = "~/.kube/config"
  config_context = "docker-desktop"
}
