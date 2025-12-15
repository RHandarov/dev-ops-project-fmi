package fmi.sports.tournament.organizer.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {
    private static final Long ID_NOT_SET = -1L;

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .creationDate(user.getCreationDate())
                .isActive(user.isActive())
                .build();
    }

    private Long id;

    @Size(min = 4, max = 20, message = "First Name must be between 4 and 20 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only English letters")
    private String firstName;

    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Username must contain only English letters")
    private String lastName;

    @Email(message = "Not a valid Email pattern")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private LocalDate creationDate;
    private boolean isActive;
}
