package fmi.sports.tournament.organizer.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final MessageDigest passwordEncoder;

    @Autowired
    public PasswordServiceImpl(MessageDigest passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean verify(String comparingPassword, String hashedPassword) {
        return hashedPassword.equals(hash(comparingPassword));
    }

    @Override
    public String hash(String password) {
        byte[] hashedPassword =
                passwordEncoder.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
