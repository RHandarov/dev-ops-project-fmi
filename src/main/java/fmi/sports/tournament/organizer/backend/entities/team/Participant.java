package fmi.sports.tournament.organizer.backend.entities.team;

import fmi.sports.tournament.organizer.backend.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "participants")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Participant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private ParticipantCategory category;

    public Participant(User user, Team team, ParticipantCategory category) {
        this.user = user;
        this.team = team;
        this.category = category;
    }
}
