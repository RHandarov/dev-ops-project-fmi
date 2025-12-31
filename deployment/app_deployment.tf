resource "kubernetes_deployment_v1" "app_deployment" {
  metadata {
    name = "myapp"
    namespace = kubernetes_namespace_v1.myapp.metadata[0].name
  }
  spec {
    replicas = 3
    selector {
      match_labels = {
        app = "myapp"
      }
    }
    strategy {
      type = "RollingUpdate"
    }
    template {
      metadata {
        namespace = kubernetes_namespace_v1.myapp.metadata[0].name
        labels = {
            app = "myapp"
        }
      }
      spec {
        container {
          name = "myapp"
          image = "ghcr.io/rhandarov/myapp:latest"
          env_from {
            config_map_ref {
              name = "db-config-map"
            }
          }
          env_from {
            secret_ref {
              name = "db-secrets"
            }
          }
          port {
            container_port = 8080
          }
        }
      }
    }
  }
}