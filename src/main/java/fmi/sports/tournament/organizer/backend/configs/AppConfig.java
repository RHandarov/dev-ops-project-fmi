package fmi.sports.tournament.organizer.backend.configs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  @Bean
  public MessageDigest passwordEncoder() throws NoSuchAlgorithmException {
    return MessageDigest.getInstance("SHA-512");
  }
}
