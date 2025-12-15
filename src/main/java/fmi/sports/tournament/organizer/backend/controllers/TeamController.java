package fmi.sports.tournament.organizer.backend.controllers;

import fmi.sports.tournament.organizer.backend.dtos.*;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import fmi.sports.tournament.organizer.backend.responses.ParticipantResponse;
import fmi.sports.tournament.organizer.backend.responses.ResponseResult;
import fmi.sports.tournament.organizer.backend.responses.TeamResponse;
import fmi.sports.tournament.organizer.backend.responses.UserResponse;
import fmi.sports.tournament.organizer.backend.services.JWTService;
import fmi.sports.tournament.organizer.backend.services.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;
    private final JWTService jwtService;

    @Autowired
    public TeamController(TeamService teamService, JWTService jwtService) {
        this.teamService = teamService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<TeamResponse> getAll() {
        return teamService
                .getAll()
                .stream()
                .map(teamDTO -> TeamResponse
                        .fromDTO(teamDTO)
                        .responseResult(ResponseResult.SUCCESSFULLY_FOUND)
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getById(@PathVariable("id") Long teamId) {
        TeamDTO dto = teamService.getById(teamId);
        TeamResponse response = TeamResponse
                .fromDTO(dto)
                .responseResult(ResponseResult.SUCCESSFULLY_FOUND)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TeamResponse> create(@Valid @RequestBody TeamDTO newTeam) {
        TeamDTO teamDTO = teamService.create(newTeam);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        TeamResponse
                                .fromDTO(teamDTO)
                                .responseResult(ResponseResult.SUCCESSFULLY_CREATED)
                                .build()
                );
    }

    @PutMapping
    public ResponseEntity<TeamResponse> update(@Valid @RequestBody TeamDTO updatedTeams) {
        TeamDTO updated = teamService.update(updatedTeams);
        return ResponseEntity.ok(
                TeamResponse
                        .fromDTO(updated)
                        .responseResult(ResponseResult.SUCCESSFULLY_UPDATED)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TeamResponse> deleteById(@PathVariable("id") Long teamId) {
        TeamDTO teamDTO = teamService.deleteById(teamId);
        return ResponseEntity.ok(
                TeamResponse
                        .fromDTO(teamDTO)
                        .responseResult(ResponseResult.SUCCESSFULLY_DELETED)
                        .build()
        );
    }

    @PostMapping("/{teamId}/participants")
    public ResponseEntity<String> registerUserForTeam(@RequestHeader("Authorization") String authorizationHeader,
                                                      @RequestBody ParticipantRegisterDTO participantRegisterDTO, @PathVariable("teamId") Long teamId) {
        String token = authorizationHeader.startsWith("Bearer ") ?
                authorizationHeader.substring(7) : authorizationHeader;
        User user = jwtService.getUser(token);
        Long userId = user.getId();
        teamService.registerUserForTeam(userId, teamId, participantRegisterDTO);
        return ResponseEntity.ok("User successfully registered for team with id " + teamId);
    }

    @DeleteMapping("/{teamId}/participants")
    public ResponseEntity<TeamResponse> removeUserFromTeam(@RequestHeader("Authorization") String authorizationHeader,
                                                           @PathVariable("teamId") Long teamId) {
        String token = authorizationHeader.startsWith("Bearer ") ?
                authorizationHeader.substring(7) : authorizationHeader;
        User user = jwtService.getUser(token);
        Long userId = user.getId();
        teamService.removeUserForTeam(userId, teamId);

        return ResponseEntity.ok(
                TeamResponse
                        .builder()
                        .message(String.format("User with id %d successfully removed from team with id %d", userId, teamId))
                        .responseResult(ResponseResult.SUCCESSFULLY_REMOVED_FROM_TEAM)
                        .build()
        );
    }

    @GetMapping("/{teamId}/participants")
    public List<ParticipantResponse> getAllParticipantsForTeam(@PathVariable("teamId") Long teamId) {
        return teamService
                .getAllParticipantsForTeam(teamId)
                .stream()
                .map(participantDTO -> ParticipantResponse.fromDTO(participantDTO).build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{teamId}/participants/{participantId}")
    public ResponseEntity<ParticipantResponse> getParticipantForTeamById(@PathVariable("teamId") Long teamId,
                                                                         @PathVariable("participantId") Long participantId) {
        ParticipantDTO participant = teamService.getParticipantInTeamById(teamId, participantId);
        return ResponseEntity.ok()
                .body(ParticipantResponse
                        .fromDTO(participant)
                        .responseResult(ResponseResult.SUCCESSFULLY_FOUND)
                        .build());
    }

    @PutMapping("/{teamId}/participants")
    public ResponseEntity<ParticipantResponse> updateParticipantCategory(@PathVariable("teamId") Long teamId,
                                                                         @RequestBody ChangeParticipantCategoryDTO participantCategoryDTO) {
        ParticipantDTO updatedParticipantDTO = teamService.updateParticipantCategory(teamId, participantCategoryDTO);
        return ResponseEntity.ok(
                ParticipantResponse
                        .fromDTO(updatedParticipantDTO)
                        .responseResult(ResponseResult.SUCCESSFULLY_UPDATED)
                        .build()
        );
    }

    @PostMapping("/{teamId}/followers/{userId}")
    public void subscribeForTeam(@PathVariable("userId") Long userId,
                                 @PathVariable("teamId") Long teamId) {
        teamService.subscribeUserForTeam(teamId, userId);
    }

    @DeleteMapping("/{teamId}/followers/{userId}")
    public void unsubscribeFromTeam(@PathVariable("userId") Long userId,
                                    @PathVariable("teamId") Long teamId) {
        teamService.unsubscribeFromTeam(teamId, userId);
    }

    @GetMapping("/{teamId}/followers")
    public List<UserResponse> getAllFollowersForTeam(@PathVariable("teamId") Long teamId) {
        return teamService
                .getAllFollowers(teamId)
                .stream()
                .map(userDTO -> UserResponse.fromDTO(userDTO).build())
                .collect(Collectors.toList());
    }
}