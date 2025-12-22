resource "kubernetes_secret_v1" "secrets" {
  metadata {
    name = "db-secrets"
    namespace = kubernetes_namespace_v1.myapp.metadata[0].name
  }
  data = {
    DB_USERNAME = "root"
    DB_PASSWORD = "secret"
  }
  immutable = true
}