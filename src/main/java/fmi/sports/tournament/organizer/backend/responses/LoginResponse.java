package fmi.sports.tournament.organizer.backend.responses;

import fmi.sports.tournament.organizer.backend.dtos.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginResponse {
    public static LoginResponseBuilder fromDTO(LoginDTO loginDTO) {
        return LoginResponse.builder()
                .token(loginDTO.getToken())
                .expires(loginDTO.getExpires());
    }

    private String token;
    private LocalDateTime expires;
    private ResponseResult responseResult;
    private String message;
}
