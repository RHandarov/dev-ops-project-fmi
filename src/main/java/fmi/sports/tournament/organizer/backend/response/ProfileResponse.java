package fmi.sports.tournament.organizer.backend.response;

import fmi.sports.tournament.organizer.backend.dtos.NewUserDTO;
import fmi.sports.tournament.organizer.backend.dtos.UserDTO;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import fmi.sports.tournament.organizer.backend.entities.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProfileResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private LocalDate creationDate;
    private boolean isActive;
    private ResponseResult responseResult;
    private String message;
    private Map<String, String> errors;
    private UserRole userRole;

    public static ProfileResponseBuilder fromDTO(NewUserDTO userDTO) {
        return ProfileResponse.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .birthDate(userDTO.getBirthDate());
    }

    public static ProfileResponseBuilder fromEntity(User user) {
        return ProfileResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .creationDate(user.getCreationDate())
                .isActive(user.isActive());
    }
}
