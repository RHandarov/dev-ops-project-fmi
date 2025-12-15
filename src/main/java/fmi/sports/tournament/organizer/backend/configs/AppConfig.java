package fmi.sports.tournament.organizer.backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class AppConfig {
    @Bean
    public MessageDigest passwordEncoder() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-512");
    }
}
