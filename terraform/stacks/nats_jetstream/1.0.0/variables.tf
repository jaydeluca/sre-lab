variable "chart" {
  default = "nats"
  type    = string
}

variable "chart_name" {
  default = "nats-jetstream"
  type    = string
}

variable "chart_version" {
  default = "0.19.5"
  type    = string
}

variable "namespace" {
  default = "nats"
  type    = string
}

variable "repository" {
  default = "https://nats-io.github.io/k8s/helm/charts/"
  type    = string
}

variable "helm_overrides" {
  type = map(object({
    name  = string
    value = any
  }))

  default = {
    "jetstream_enabled" = {
      name  = "jetstream.enabled"
      value = true
    }
  }
}
