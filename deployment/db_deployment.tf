resource "kubernetes_deployment_v1" "db_deployment" {
  metadata {
    name = "db"
    namespace = kubernetes_namespace_v1.myapp.metadata[0].name
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        app = "db"
      }
    }
    strategy {
      type = "RollingUpdate"
    }
    template {
      metadata {
        namespace = kubernetes_namespace_v1.myapp.metadata[0].name
        labels = {
          app = "db"
        }
      }
      spec {
        container {
          name = "db"
          image = "mysql:9.5.9"
          
          port {
            container_port = 3306
          }
        }
      }
    }
  }
}