resource "kubernetes_service_v1" "db_service" {
  metadata {
    name = "db-service"
    namespace = kubernetes_namespace_v1.myapp.metadata[0].name
    labels = {
      app = "db_service"
    }
  }
  spec {
    selector = {
        app = "db"
    }
    port {
      port = 3306
      target_port = 3306
    }
  }
}