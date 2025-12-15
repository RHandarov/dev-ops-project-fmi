package fmi.sports.tournament.organizer.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import fmi.sports.tournament.organizer.backend.entities.tournament.Tournament;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TournamentDTO {
    public static final Long ID_NOT_SET = -1L;

    public static TournamentDTO fromEntity(Tournament tournament) {
        TournamentDTO dto = new TournamentDTO(tournament.getName(),
                tournament.getLocation(),
                tournament.getSportType(),
                tournament.getStartDate(),
                tournament.getEndDate(),
                tournament.getRegistrationFee(),
                tournament.getMaxTeams());
        dto.setId(tournament.getId());

        return dto;
    }

    private Long id;

    @NotNull(message = "Tournament Name is required")
    @NotEmpty(message = "Tournament Name must not be empty")
    private String name;

    @NotNull(message = "Location is required")
    @NotEmpty(message = "Location must not be empty")
    private String location;

    @NotNull(message = "Sport type is required")
    @NotEmpty(message = "Sport type must not be empty")
    private String sportType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the past")
    private LocalDate endDate;

    @NotNull(message = "Registration fee is required")
    @Positive(message = "Registration fee must be a positive number")
    private Double registrationFee;

    @NotNull(message = "Max team number is required")
    @Positive(message = "Max Team must be a positive number")
    private Integer maxTeams;

    public TournamentDTO(String name,
                         String location,
                         String sportType,
                         LocalDate startDate,
                         LocalDate endDate,
                         Double registrationFee,
                         Integer maxTeams) {
        this.id = ID_NOT_SET;
        this.name = name;
        this.location = location;
        this.sportType = sportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationFee = registrationFee;
        this.maxTeams = maxTeams;
    }
}
