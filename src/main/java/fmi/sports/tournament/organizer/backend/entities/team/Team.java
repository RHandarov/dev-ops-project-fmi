package fmi.sports.tournament.organizer.backend.entities.team;

import fmi.sports.tournament.organizer.backend.entities.tournament.Tournament;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "teams")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Double budget;
    private Integer size;
    private String secretCode;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participant> participants;

    @ManyToMany(mappedBy = "followedTeams")
    private Set<User> followers;

    @ManyToMany(mappedBy = "teams", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Tournament> tournaments;

    public Team(String name,
                String email,
                Double budget,
                Integer size,
                String secretCode) {
        this.name = name;
        this.email = email;
        this.budget = budget;
        this.size = size;
        this.secretCode = secretCode;
    }
}
