package fmi.sports.tournament.organizer.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import fmi.sports.tournament.organizer.backend.entities.team.Team;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TeamDTO {
    private static final Long ID_NOT_SET = -1L;

    public static TeamDTO fromEntity(Team team) {
        return new TeamDTO(team.getId(),
                team.getName(),
                team.getEmail(),
                team.getBudget(),
                team.getSize(),
                team.getSecretCode());
    }

    private Long id;

    @JsonProperty(value = "teamName")
    @NotNull(message = "Team name is required")
    @NotEmpty(message = "Team name must not be empty")
    private String name;

    @Email(message = "Not a valid Email pattern")
    @JsonProperty(value = "contactEmail")
    private String email;

    @Positive(message = "Budget must be a positive number")
    private Double budget;

    @JsonProperty(value = "maxMembers")
    @Positive(message = "Max team members must be a positive number")
    private Integer size;
    private String secretCode;

    public TeamDTO() {
        this.id = ID_NOT_SET;
    }

    public TeamDTO(Long id,
                   String name,
                   String email,
                   Double budget,
                   Integer size,
                   String secretCode) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.budget = budget;
        this.size = size;
        this.secretCode = secretCode;
    }
}
