resource "helm_release" "chart" {
  name       = var.chart_name
  repository = var.repository
  chart      = var.chart
  version    = var.chart_version

  namespace = var.namespace

  dynamic "set" {
    for_each = var.chart_value_overides
    content {
      name  = set.value.name
      value = set.value.value
    }
  }

  provisioner "local-exec" {
    when    = destroy
    command = "echo 'Destroy-time provisioner' && sleep 60 && echo 'done'"
  }
}
