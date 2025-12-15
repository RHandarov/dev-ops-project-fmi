package fmi.sports.tournament.organizer.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewUserDTO {
    @Email(message = "Not a valid Email pattern")
    private String email;

    @Pattern(
            regexp = "^[\\p{Print}&&[^\\s]]{8,128}$",
            message = "Password must be between 8 and 128 characters and must not contain whitespace"
    )
    private String password;

    @Size(min = 4, max = 20, message = "First Name must be between 4 and 20 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only English letters")
    private String firstName;

    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Username must contain only English letters")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
}
