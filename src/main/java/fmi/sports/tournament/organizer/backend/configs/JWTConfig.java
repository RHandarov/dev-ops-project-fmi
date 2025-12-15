package fmi.sports.tournament.organizer.backend.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JWTConfig {
    private String signingSecret;
    private long expiration;
}
