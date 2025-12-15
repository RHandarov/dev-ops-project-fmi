package fmi.sports.tournament.organizer.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CredentialsDTO {
    private String email;
    private String password;
}
