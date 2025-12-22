resource "kubernetes_config_map_v1" "config_map" {
  metadata {
    name = "db-config-map"
    namespace = kubernetes_namespace_v1.myapp.metadata[0].name
  }
  data = {
    DB_URL = "jdbc:mysql://db-service.myapp-namespace.svc.cluster.local:3306/sports_tournament_organizer?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false"
  }
  immutable = true
}