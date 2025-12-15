package fmi.sports.tournament.organizer.backend.entities.auth;

import fmi.sports.tournament.organizer.backend.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "sessions")
public class Session {
    private static final int TOKEN_LENGTH = 120;

    @Id
    private Long userId;

    @MapsId
    @OneToOne
    private User user;
    private String sessionId;
    private LocalDateTime expires;

    public Session(User user, LocalDateTime expires) {
        this.user = user;
        this.sessionId = TokenGenerator.generateRandomToken(TOKEN_LENGTH);
        this.expires = expires;
    }
}
