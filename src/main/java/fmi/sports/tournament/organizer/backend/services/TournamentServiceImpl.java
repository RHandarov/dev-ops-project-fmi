package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.StandingDTO;
import fmi.sports.tournament.organizer.backend.dtos.TeamDTO;
import fmi.sports.tournament.organizer.backend.dtos.TournamentDTO;
import fmi.sports.tournament.organizer.backend.entities.team.Team;
import fmi.sports.tournament.organizer.backend.entities.tournament.Standing;
import fmi.sports.tournament.organizer.backend.entities.tournament.StandingId;
import fmi.sports.tournament.organizer.backend.entities.tournament.Tournament;
import fmi.sports.tournament.organizer.backend.exceptions.team.NoTeamWithSuchIdException;
import fmi.sports.tournament.organizer.backend.exceptions.time.InappropriateMomentException;
import fmi.sports.tournament.organizer.backend.exceptions.tournament.NoPlacesAvailableException;
import fmi.sports.tournament.organizer.backend.exceptions.tournament.NoSufficientMoneyException;
import fmi.sports.tournament.organizer.backend.exceptions.tournament.NoTournamentWithSuchIdException;
import fmi.sports.tournament.organizer.backend.exceptions.team.TeamAlreadyRegisteredException;
import fmi.sports.tournament.organizer.backend.repositories.StandingsRepository;
import fmi.sports.tournament.organizer.backend.repositories.TeamsRepository;
import fmi.sports.tournament.organizer.backend.repositories.TournamentsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentServiceImpl implements TournamentService {
    private final TournamentsRepository tournamentsRepository;
    private final TeamsRepository teamsRepository;
    private final StandingsRepository standingsRepository;

    @Autowired
    public TournamentServiceImpl(TournamentsRepository tournamentsRepository,
                                 TeamsRepository teamsRepository,
                                 StandingsRepository standingsRepository) {
        this.tournamentsRepository = tournamentsRepository;
        this.teamsRepository = teamsRepository;
        this.standingsRepository = standingsRepository;
    }

    @Override
    public TournamentDTO create(TournamentDTO newTournament) {
        Tournament entity = new Tournament(newTournament.getName(),
                newTournament.getLocation(),
                newTournament.getSportType(),
                newTournament.getStartDate(),
                newTournament.getEndDate(),
                newTournament.getRegistrationFee(),
                newTournament.getMaxTeams());

        return TournamentDTO.fromEntity(tournamentsRepository.save(entity));
    }

    @Override
    public List<TournamentDTO> getAll() {
        return tournamentsRepository.findAll()
                .stream()
                .map(TournamentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TournamentDTO getById(Long tournamentId) {
        Tournament tournament = tournamentsRepository.findById(tournamentId).orElseThrow(
                () -> new NoTournamentWithSuchIdException(String.format("Tournament with id %d does not exist", tournamentId))
        );
        return TournamentDTO.fromEntity(tournament);
    }

    @Override
    public void updateById(TournamentDTO updatedTournament, long id) {
        tournamentsRepository
                .findById(id)
                .orElseThrow(() -> new NoTournamentWithSuchIdException(String.format("Tournament with id %d does not exist", id)));

        Tournament tournament = Tournament.fromDTO(updatedTournament);
        tournament.setId(id);
        tournamentsRepository.save(tournament);
    }

    @Override
    public TournamentDTO deleteById(Long tournamentId) {
        Tournament tournament = getTournamentEntityById(tournamentId);
        TournamentDTO tournamentDTO = TournamentDTO.fromEntity(tournament);
        tournamentsRepository.deleteById(tournamentId);
        return tournamentDTO;
    }

    @Override
    public List<TeamDTO> getAllParticipatingTeams(Long tournamentId) {
        return getTournamentEntityById(tournamentId)
                .getTeams()
                .stream()
                .map(TeamDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void registerTeamForParticipation(Long tournamentId, Long teamId) {
        Tournament tournament = getTournamentEntityById(tournamentId);

        if (LocalDate.now().isAfter(tournament.getStartDate())
                || LocalDate.now().equals(tournament.getStartDate())) {
            throw new InappropriateMomentException("Team cannot register after start of tournament!");
        }

        Team team = getTeamEntityById(teamId);

        if (tournament.getTeams().contains(team)) {
            throw new TeamAlreadyRegisteredException(
                    String.format("Team with id %d already registered for Tournament with id %d.", team.getId(), tournament.getId())
            );
        }

        if (team.getBudget() < tournament.getRegistrationFee()) {
            throw new NoSufficientMoneyException("Not enough money for registration for this tournament!");
        }

        if (tournament.getTeams().size() == tournament.getMaxTeams()) {
            throw new NoPlacesAvailableException("There are no more places for registration in this tournament!");
        }

        tournament.getTeams().add(team);
        team.setBudget(team.getBudget() - tournament.getRegistrationFee());
        tournamentsRepository.save(tournament);
        teamsRepository.save(team);
        standingsRepository.save(Standing
                .builder()
                .tournament(tournament)
                .team(team)
                .points(0)
                .build());
    }

    @Override
    public void unregisterTeamForParticipation(Long tournamentId, Long teamId) {
        Tournament tournament = getTournamentEntityById(tournamentId);
        if (LocalDate.now().isAfter(tournament.getStartDate())
                || LocalDate.now().equals(tournament.getStartDate())) {
            throw new InappropriateMomentException("Team cannot unregister after start of tournament!");
        }

        Team team = getTeamEntityById(teamId);

        if (!tournament.getTeams().contains(team)) {
            return;
        }

        tournament.getTeams().remove(team);
        team.setBudget(team.getBudget() + tournament.getRegistrationFee());
        tournamentsRepository.save(tournament);
        teamsRepository.save(team);
        standingsRepository.deleteById(StandingId
                .builder()
                .tournamentId(tournament.getId())
                .teamId(team.getId())
                .build());
    }

    @Override
    public List<StandingDTO> getTournamentStandings(Long tournamentId) {
        getTournamentEntityById(tournamentId); // for validation purpose

        return standingsRepository
                .findByTournamentId(tournamentId)
                .stream()
                .map(StandingDTO::fromEntity)
                .sorted((lhs, rhs) -> {
                    if (lhs.getPoints().equals(rhs.getPoints())) {
                        return lhs.getTeamId().compareTo(rhs.getTeamId());
                    }

                    return lhs.getPoints().compareTo(rhs.getPoints()) * -1; // for reverse order
                })
                .collect(Collectors.toList());
    }

    private Tournament getTournamentEntityById(Long tournamentId) {
        return tournamentsRepository.findById(tournamentId).orElseThrow(
                () -> new NoTournamentWithSuchIdException(String.format("Tournament with id %d does not exist", tournamentId))
        );
    }

    private Team getTeamEntityById(Long teamId) {
        return teamsRepository.findById(teamId).orElseThrow(
                () -> new NoTeamWithSuchIdException(String.format("Team with id %d doesn't exist!", teamId))
        );
    }
}