package fmi.sports.tournament.organizer.backend.responses;

import fmi.sports.tournament.organizer.backend.dtos.NewUserDTO;
import fmi.sports.tournament.organizer.backend.dtos.UserDTO;
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
public class UserResponse {
    public static UserResponseBuilder fromDTO(UserDTO userDTO) {
        return UserResponse.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .birthDate(userDTO.getBirthDate())
                .creationDate(userDTO.getCreationDate())
                .isActive(userDTO.isActive());
    }

    public static UserResponseBuilder fromDTO(NewUserDTO newUserDTO) {
        return UserResponse.builder()
                .firstName(newUserDTO.getFirstName())
                .lastName(newUserDTO.getLastName())
                .email(newUserDTO.getEmail())
                .birthDate(newUserDTO.getBirthDate());
    }

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
}
