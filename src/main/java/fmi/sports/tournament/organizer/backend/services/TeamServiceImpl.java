package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.*;
import fmi.sports.tournament.organizer.backend.entities.auth.TokenGenerator;
import fmi.sports.tournament.organizer.backend.entities.team.Participant;
import fmi.sports.tournament.organizer.backend.entities.team.Team;
import fmi.sports.tournament.organizer.backend.entities.tournament.Tournament;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import fmi.sports.tournament.organizer.backend.exceptions.team.*;
import fmi.sports.tournament.organizer.backend.exceptions.user.NoUserWithSuchIdException;
import fmi.sports.tournament.organizer.backend.repositories.ParticipantsRepository;
import fmi.sports.tournament.organizer.backend.repositories.TeamsRepository;
import fmi.sports.tournament.organizer.backend.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {
    private static final int SECRET_CODE_LENGTH = 9;

    private final TeamsRepository teamsRepository;
    private final UsersRepository userRepository;
    private final ParticipantsRepository participantsRepository;

    @Autowired
    public TeamServiceImpl(TeamsRepository teamsRepository, UsersRepository userRepository, ParticipantsRepository participantsRepository) {
        this.teamsRepository = teamsRepository;
        this.userRepository = userRepository;
        this.participantsRepository = participantsRepository;
    }

    @Override
    public TeamDTO create(TeamDTO newTeam) {
        Team newEntity = new Team(newTeam.getName(),
                newTeam.getEmail(),
                newTeam.getBudget(),
                newTeam.getSize(),
                TokenGenerator.generateRandomToken(SECRET_CODE_LENGTH));
        return TeamDTO.fromEntity(teamsRepository.save(newEntity));
    }

    @Override
    public TeamDTO getById(Long teamId) {
        TeamDTO teamDTO = teamsRepository.findById(teamId)
                .map(TeamDTO::fromEntity).orElseThrow(
                        () -> new NoTeamWithSuchIdException(String.format("Team with id %d does not exist", teamId))
                );
        return teamDTO;
    }

    @Override
    public List<TeamDTO> getAll() {
        return teamsRepository.findAll()
                .stream()
                .map(TeamDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TeamDTO update(TeamDTO updatedTeam) {
        Team team = getTeamEntityById(updatedTeam.getId());
        team.setName(updatedTeam.getName());
        team.setEmail(updatedTeam.getEmail());
        team.setBudget(updatedTeam.getBudget());
        team.setSize(updatedTeam.getSize());
        if (updatedTeam.getSecretCode() != null) {
            team.setSecretCode(updatedTeam.getSecretCode());
        }
        Team saved = teamsRepository.save(team);
        return TeamDTO.fromEntity(saved);
    }

    @Transactional
    @Override
    public TeamDTO deleteById(Long teamId) {
        Team team = getTeamEntityById(teamId);

        for (Tournament tournament : team.getTournaments()) {
            tournament.getTeams().remove(team);
        }

        team.getTournaments().clear();

        participantsRepository.deleteByTeamId(teamId);
        team.getParticipants().clear();

        teamsRepository.save(team);
        teamsRepository.delete(team);

        return TeamDTO.fromEntity(team);
    }


    @Override
    @Transactional
    public void registerUserForTeam(Long userId, Long teamId, ParticipantRegisterDTO participantRegisterDTO) {
        User user = getUserEntityById(userId);
        Team team = getTeamEntityById(teamId);

        if (!team.getSecretCode().equals(participantRegisterDTO.getSecretCode())) {
            throw new InvalidSecretCodeException(String.format("Invalid secret code for team with id %d", teamId));
        }

        if (team.getParticipants().stream().map(Participant::getUser).toList().contains(user)) {
            throw new UserAlreadyRegisteredForTeamException(String.format("User with id %d is already registered for team with id %d", userId, teamId));
        }

        Optional<Participant> participantByUserId = participantsRepository.findByUserId(userId);
        participantByUserId.ifPresent(participant -> {
            if (!teamId.equals(participantByUserId.get().getTeam().getId())) {
                throw new UserAlreadyRegisteredForAnotherTeamException(String.format(
                        "User with id %d is already registered for a different team",userId));
            }
        });

        if (team.getParticipants().size() >= team.getSize()) {
            throw new TeamAlreadyAtMaxSizeException(String.format("Team with id %d is already at full size", teamId));
        }

        Participant participant = Participant
                .builder()
                .team(team)
                .user(user)
                .category(participantRegisterDTO.getCategory())
                .build();

        team.getParticipants().add(participant);
        teamsRepository.save(team);

    }

    @Override
    @Transactional
    public void removeUserForTeam(Long userId, Long teamId) {
        Team team = getTeamEntityById(teamId);
        User user = getUserEntityById(userId);
        Set<Participant> participants = team.getParticipants();
        List<Participant> list = participants.stream().filter(p -> p.getUser().equals(user)).toList();

        if (list.isEmpty()) {
            throw new UserNotInTeamException(String.format("User with id %d is not in team with id %d", userId, teamId));
        }

        Participant participant = list.getFirst();

        participant.setTeam(null);
        participant.setUser(null);

        participants.remove(participant);
    }

    @Override
    public List<ParticipantDTO> getAllParticipantsForTeam(Long teamId) {
        return participantsRepository.findAllByTeamId(teamId)
                .stream()
                .map(ParticipantDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipantDTO getParticipantInTeamById(Long teamId, Long participantId) {
        return getAllParticipantsForTeam(teamId)
                .stream()
                .filter(participant -> participant.getUserId().equals(participantId))
                .findFirst()
                .orElseThrow(() -> new NoParticipantWithIdException(participantId));
    }

    @Override
    public ParticipantDTO updateParticipantCategory(Long teamId, ChangeParticipantCategoryDTO participantCategoryDTO) {
        Participant participant =
                participantsRepository.findByTeamIdAndUserId(teamId, participantCategoryDTO.getUserId())
                        .orElseThrow(() -> new NoParticipantWithIdException(participantCategoryDTO.getUserId()));

        participant.setCategory(participantCategoryDTO.getParticipantCategory());
        return ParticipantDTO.fromEntity(participantsRepository.save(participant));
    }

    @Override
    public void subscribeUserForTeam(Long teamId, Long userId) {
        User user = getUserEntityById(userId);
        Team team = getTeamEntityById(teamId);
        user.getFollowedTeams().add(team);
        userRepository.save(user);
    }

    @Override
    public void unsubscribeFromTeam(Long teamId, Long userId) {
        User user = getUserEntityById(userId);
        Team team = getTeamEntityById(teamId);
        user.getFollowedTeams().remove(team);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllFollowers(Long teamId) {
        Team team = getTeamEntityById(teamId);
        return team.getFollowers()
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private Team getTeamEntityById(Long teamId) {
        return teamsRepository.findById(teamId).orElseThrow(
                () -> new NoTeamWithSuchIdException(String.format("Team with id %d does not exist", teamId))
        );
    }

    private User getUserEntityById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NoUserWithSuchIdException(String.format("User with id %d does not exist", userId))
        );
    }
}