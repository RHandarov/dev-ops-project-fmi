resource "kubernetes_service_v1" "myapp_service" {
  metadata {
    name = "myapp-service"
    namespace = kubernetes_namespace_v1.myapp.metadata[0].name
    labels = {
      app = "myapp-service"
    }
  }
  spec {
    port {
      port = 8080
      target_port = 8080
    }
    selector = {
        app = "myapp"
    }
  }
}