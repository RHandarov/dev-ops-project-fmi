resource "kubernetes_deployment_v1" "app_deployment" {
  metadata {
    name = "myapp"
    namespace = kubernetes_namespace_v1.myapp.metadata[0].name
  }
  spec {
    replicas = 1
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
          # image_pull_policy = "Never"
          # env {
          #   name = "DB_URL"
          #   value = "jdbc:mysql://db-service.myapp-namespace.svc.cluster.local:3306/sports_tournament_organizer?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false"
          # }
          env_from {
            config_map_ref {
              name = "db-config-map"
            }
          }
          # env {
          #   name = "DB_USERNAME"
          #   value = "root"
          # }
          # env {
          #   name = "DB_PASSWORD"
          #   value = "secret"
          # }
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