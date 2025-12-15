package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.configs.JWTConfig;
import fmi.sports.tournament.organizer.backend.dtos.UserDTO;
import fmi.sports.tournament.organizer.backend.entities.auth.Session;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import fmi.sports.tournament.organizer.backend.exceptions.user.auth.InvalidTokenException;
import fmi.sports.tournament.organizer.backend.exceptions.user.auth.NoSessionWithSuchIdException;
import fmi.sports.tournament.organizer.backend.repositories.SessionsRepository;
import fmi.sports.tournament.organizer.backend.repositories.UsersRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class JWTService {
    private final SecretKey secretKey;
    private final JWTConfig jwtConfig;
    private final SessionsRepository sessionsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public JWTService(JWTConfig jwtConfig,
                      SessionsRepository sessionsRepository,
                      UsersRepository usersRepository) {
        this.jwtConfig = jwtConfig;
        secretKey = Keys.hmacShaKeyFor(jwtConfig.getSigningSecret().getBytes(StandardCharsets.UTF_8));
        this.sessionsRepository = sessionsRepository;
        this.usersRepository = usersRepository;
    }

    public String generateToken(UserDTO userDTO) {
        Date issueDate = new Date();
        Date expirationDate = new Date(issueDate.getTime() + jwtConfig.getExpiration());
        String token = Jwts.builder()
                .subject(userDTO.getEmail())
                .issuedAt(issueDate)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();

        saveToken(token, userDTO, expirationDate);

        return token;
    }

    private void saveToken(String token, UserDTO userDTO, Date expirationDate) {
        Optional<Session> sessionOptional = sessionsRepository.findById(userDTO.getId());
        Session session;
        if (sessionOptional.isEmpty()) {
            User user = usersRepository.findById(userDTO.getId()).get();
            session = Session.builder()
                    .sessionId(token)
                    .expires(
                            expirationDate
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                    )
                    .user(user)
                    .build();
        } else {
            session = sessionOptional.get();
            session.setSessionId(token);
            session.setExpires(
                    expirationDate
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
            );
        }

        sessionsRepository.save(session);
    }

    public UserDTO getUserDTO(String token) {
        if (!isTokenValid(token)) {
            throw new InvalidTokenException("This JWT token is not valid!");
        }

        Optional<Session> sessionOptional = sessionsRepository.findBySessionId(token);
        return UserDTO.fromEntity(sessionOptional.get().getUser());
    }

    public User getUser(String token) {
        if (!isTokenValid(token)) {
            throw new InvalidTokenException("This JWT token is not valid!");
        }

        Optional<Session> sessionOptional = sessionsRepository.findBySessionId(token);
        return sessionOptional.orElseThrow(
                () -> new NoSessionWithSuchIdException("This session does not exist")
        ).getUser();
    }

    public LocalDateTime getExpirationTime(String token) {
        if (!isTokenValid(token)) {
            throw new InvalidTokenException("This JWT token is not valid!");
        }

        return sessionsRepository
                .findBySessionId(token)
                .get()
                .getExpires();
    }

    public boolean isTokenValid(String token) {
        if (sessionsRepository.findBySessionId(token).isEmpty()) {
            return false;
        }

        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parse(token);
        } catch (Exception _) {
            return false;
        }

        return true;
    }
}
