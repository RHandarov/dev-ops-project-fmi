package fmi.sports.tournament.organizer.backend.services;

import org.springframework.stereotype.Service;

@Service
public interface PasswordService {
    boolean verify(String comparingPassword, String hashedPassword);
    String hash(String password);
}
