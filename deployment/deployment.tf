resource "kubernetes_deployment_v1" "app_deployment" {
  metadata {
    name = "myapp"
    namespace = kubernetes_namespace_v1.myapp.metadata[0].name
  }
  spec {
    replicas = 2
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
          image = "myapp:0.0.1"
          image_pull_policy = "Never"
          port {
            container_port = 8080
          }
        }
      }
    }
  }
}