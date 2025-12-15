package fmi.sports.tournament.organizer.backend.controllers;

import fmi.sports.tournament.organizer.backend.dtos.CredentialsDTO;
import fmi.sports.tournament.organizer.backend.dtos.NewUserDTO;
import fmi.sports.tournament.organizer.backend.dtos.UserDTO;
import fmi.sports.tournament.organizer.backend.responses.LoginResponse;
import fmi.sports.tournament.organizer.backend.responses.ResponseResult;
import fmi.sports.tournament.organizer.backend.responses.UserResponse;
import fmi.sports.tournament.organizer.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerNewUser(@Valid @RequestBody NewUserDTO newUserDTO) {
        UserDTO newUser = userService.registerUser(newUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.fromDTO(newUser)
                        .responseResult(ResponseResult.SUCCESSFULLY_REGISTERED)
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody CredentialsDTO credentialsDTO) {
        return ResponseEntity.ok(
                LoginResponse.fromDTO(userService.login(credentialsDTO))
                        .responseResult(ResponseResult.SUCCESSFULLY_SIGNED_IN)
                        .build()
        );
    }
}
