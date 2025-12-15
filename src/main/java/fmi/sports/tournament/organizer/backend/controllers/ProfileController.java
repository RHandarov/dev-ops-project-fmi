package fmi.sports.tournament.organizer.backend.controllers;

import fmi.sports.tournament.organizer.backend.dtos.NewUserDTO;
import fmi.sports.tournament.organizer.backend.dtos.UserDTO;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import fmi.sports.tournament.organizer.backend.entities.user.UserRole;
import fmi.sports.tournament.organizer.backend.responses.ProfileResponse;
import fmi.sports.tournament.organizer.backend.responses.ResponseResult;
import fmi.sports.tournament.organizer.backend.responses.UserResponse;
import fmi.sports.tournament.organizer.backend.services.JWTService;
import fmi.sports.tournament.organizer.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    public ProfileController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.startsWith("Bearer ") ?
                authorizationHeader.substring(7) : authorizationHeader;
        UserDTO user = jwtService.getUserDTO(token);

        return ResponseEntity.ok(
                UserResponse
                        .fromDTO(user)
                        .responseResult(ResponseResult.VALID_TOKEN)
                        .build()
        );
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> getProfile(@RequestHeader("Authorization") String authorizationHeader,
                                                      @Valid @RequestBody NewUserDTO newUserInfo) {
        String token = authorizationHeader.startsWith("Bearer ") ?
                authorizationHeader.substring(7) : authorizationHeader;

        User user = jwtService.getUser(token);
        LocalDate creationDate = user.getCreationDate();
        UserRole userRole = user.getRole();
        Long id = user.getId();

        userService.updateUserInfo(id, newUserInfo);

        return ResponseEntity.ok(
                ProfileResponse
                        .fromDTO(newUserInfo)
                        .id(id)
                        .creationDate(creationDate)
                        .userRole(userRole)
                        .responseResult(ResponseResult.SUCCESSFULLY_UPDATED)
                        .build()
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<ProfileResponse> deleteProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.startsWith("Bearer ") ?
                authorizationHeader.substring(7) : authorizationHeader;
        User user = jwtService.getUser(token);
        Long id = user.getId();
        userService.deleteUser(id);

        return ResponseEntity.ok(
                ProfileResponse
                        .fromEntity(user)
                        .responseResult(ResponseResult.SUCCESSFULLY_DELETED)
                        .build()
        );
    }
}
