provider "digitalocean" {
  token = split(" ",regex("access-token: .*",file("~/.config/doctl/config.yaml")))[1]
}

provider "kubernetes" {
  config_path = local_file.kubeconfig.filename
}

variable "namespaces" {
  type = set(string)
  default = ["mycv", "mycv-test"]
}

resource "digitalocean_kubernetes_cluster" "mycv" {
  name = "mycv"
  region = "fra1"
  version = "1.16.6-do.2"

  node_pool {
    name = "mycv-nodepool"
    node_count = 1
    size = "s-1vcpu-2gb"
  }
}

resource "local_file" "kubeconfig" {
  filename = "kubeconfig"
  file_permission = "0600"
  content = digitalocean_kubernetes_cluster.mycv.kube_config[0].raw_config
}


resource "kubernetes_namespace" "namespace" {
  for_each = var.namespaces
  metadata {
    name = each.value
  }
}

resource "kubernetes_secret" "github" {
  for_each = kubernetes_namespace.namespace
  metadata {
    name = "github"
    namespace = each.key
  }

  data = {
    ".dockerconfigjson" = file("${path.module}/github_package_read.json")
  }

  type = "kubernetes.io/dockerconfigjson"
}


