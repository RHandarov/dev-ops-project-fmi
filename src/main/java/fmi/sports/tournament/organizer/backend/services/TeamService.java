package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamService {
    List<TeamDTO> getAll();
    TeamDTO getById(Long teamId);
    TeamDTO create(TeamDTO newTeam);
    TeamDTO update(TeamDTO updatedTeam);
    TeamDTO deleteById(Long teamId);

    void registerUserForTeam(Long userId, Long teamId, ParticipantRegisterDTO participantRegisterDTO);
    void removeUserForTeam(Long userId, Long teamId);
    List<ParticipantDTO> getAllParticipantsForTeam(Long teamId);
    ParticipantDTO getParticipantInTeamById(Long teamId, Long participantId);
    ParticipantDTO updateParticipantCategory(Long teamId, ChangeParticipantCategoryDTO participantCategoryDTO);

    void subscribeUserForTeam(Long teamId, Long userId);
    void unsubscribeFromTeam(Long teamId, Long userId);
    List<UserDTO> getAllFollowers(Long teamId);
}
