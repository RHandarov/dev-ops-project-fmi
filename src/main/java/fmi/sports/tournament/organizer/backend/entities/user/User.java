package fmi.sports.tournament.organizer.backend.entities.user;

import fmi.sports.tournament.organizer.backend.entities.team.Participant;
import fmi.sports.tournament.organizer.backend.entities.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password; // will be hashed
    private LocalDate birthDate;
    private LocalDate creationDate;
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Participant participant;

    @ManyToMany
    @JoinTable(name = "followers",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> followedTeams;

    public User(String firstName,
                String lastName,
                String email,
                String password,
                LocalDate birthDate,
                UserRole role,
                Participant participant,
                Set<Team> followedTeams) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.creationDate = LocalDate.now();
        this.isActive = false;
        this.role = role;
        this.participant = participant;
        this.followedTeams = followedTeams;
    }
}
