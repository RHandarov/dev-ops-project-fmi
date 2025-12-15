package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.MatchDTO;
import fmi.sports.tournament.organizer.backend.dtos.MatchResultsDTO;
import fmi.sports.tournament.organizer.backend.entities.team.Team;
import fmi.sports.tournament.organizer.backend.entities.tournament.Standing;
import fmi.sports.tournament.organizer.backend.entities.tournament.StandingId;
import fmi.sports.tournament.organizer.backend.entities.tournament.Tournament;
import fmi.sports.tournament.organizer.backend.entities.tournament.match.Match;
import fmi.sports.tournament.organizer.backend.entities.tournament.match.MatchStatus;
import fmi.sports.tournament.organizer.backend.exceptions.match.CompletedMatchException;
import fmi.sports.tournament.organizer.backend.exceptions.match.NoMatchWithSuchIdException;
import fmi.sports.tournament.organizer.backend.exceptions.team.NoTeamWithSuchIdException;
import fmi.sports.tournament.organizer.backend.exceptions.team.TeamNotParticipatingException;
import fmi.sports.tournament.organizer.backend.exceptions.tournament.NoTournamentWithSuchIdException;
import fmi.sports.tournament.organizer.backend.repositories.MatchesRepository;
import fmi.sports.tournament.organizer.backend.repositories.StandingsRepository;
import fmi.sports.tournament.organizer.backend.repositories.TeamsRepository;
import fmi.sports.tournament.organizer.backend.repositories.TournamentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchesRepository matchesRepository;
    private final TournamentsRepository tournamentsRepository;
    private final TeamsRepository teamsRepository;
    private final StandingsRepository standingsRepository;

    @Autowired
    public MatchServiceImpl(MatchesRepository matchesRepository,
                            TournamentsRepository tournamentsRepository,
                            TeamsRepository teamsRepository,
                            StandingsRepository standingsRepository) {
        this.matchesRepository = matchesRepository;
        this.tournamentsRepository = tournamentsRepository;
        this.teamsRepository = teamsRepository;
        this.standingsRepository = standingsRepository;
    }

    @Override
    public MatchDTO schedule(MatchDTO newMatch) {
        Tournament tournament = getTournamentEntityById(newMatch.getTournamentId());
        Team team1 = getTeamEntityById(newMatch.getTeam1Id());
        if (!tournament.getTeams().contains(team1)) {
            throw new TeamNotParticipatingException(newMatch.getTeam1Id());
        }

        Team team2 = getTeamEntityById(newMatch.getTeam2Id());
        if (!tournament.getTeams().contains(team2)) {
            throw new TeamNotParticipatingException(newMatch.getTeam2Id());
        }

        Match match = new Match(tournament, team1, team2, newMatch.getVenue());
        return MatchDTO.fromEntity(matchesRepository.save(match));
    }

    private Team getTeamEntityById(Long teamId) {
        Optional<Team> teamOptional =
                teamsRepository.findById(teamId);

        if (teamOptional.isEmpty()) {
            throw new NoTeamWithSuchIdException("Team with id " + teamId + " doesn't exists!");
        }

        return teamOptional.get();
    }

    @Override
    public List<MatchDTO> getAllForTournament(Long tournamentId) {
        Tournament tournament = getTournamentEntityById(tournamentId);
        return matchesRepository.findByTournament(tournament)
                .stream()
                .map(MatchDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private Tournament getTournamentEntityById(Long tournamentId) {
        Optional<Tournament> tournamentOptional =
                tournamentsRepository.findById(tournamentId);

        if (tournamentOptional.isEmpty()) {
            throw new NoTournamentWithSuchIdException("Tournament with id " + tournamentId + "doesn't exists!");
        }

        return tournamentOptional.get();
    }

    @Override
    public MatchDTO getById(Long matchId) {
        return MatchDTO.fromEntity(matchesRepository.findById(matchId)
                .orElseThrow(() -> new NoMatchWithSuchIdException(
                        String.format("Match with id %d does not exist!", matchId)
                )));
    }

    @Override
    public MatchDTO deleteById(Long matchId) {
        MatchDTO oldMatch = getById(matchId);
        matchesRepository.deleteById(matchId);
        return oldMatch;
    }

    @Override
    public MatchDTO updateResults(Long matchId, MatchResultsDTO newResults) {
        Match match = getMatchEntityById(matchId);
        if (match.getStatus() == MatchStatus.COMPLETED) {
            throw new CompletedMatchException(matchId);
        }

        match.setTeam1Points(newResults.getTeam1Points());
        match.setTeam2Points(newResults.getTeam2Points());
        return MatchDTO.fromEntity(matchesRepository.save(match));
    }

    @Override
    public MatchDTO markAsFinished(Long matchId) {
        Match match = getMatchEntityById(matchId);
        match.setStatus(MatchStatus.COMPLETED);
        updateStandings(match);
        return MatchDTO.fromEntity(matchesRepository.save(match));
    }

    private Match getMatchEntityById(Long matchId) {
        Optional<Match> matchOptional = matchesRepository.findById(matchId);

        if (matchOptional.isEmpty()) {
            throw new NoMatchWithSuchIdException("Match with id " + matchId + " doesn't exists!");
        }

        return matchOptional.get();
    }

    private void updateStandings(Match match) {
        Standing standing = null;
        Standing standing2 = null;
        if (match.getTeam1Points() > match.getTeam2Points()) {
            standing = getStandingForTeam(match.getTournament(), match.getTeam1());
            standing.setPoints(standing.getPoints() + 3);
        } else if (match.getTeam1Points().equals(match.getTeam2Points())) {
            standing = getStandingForTeam(match.getTournament(), match.getTeam1());
            standing2 = getStandingForTeam(match.getTournament(), match.getTeam2());
            standing.setPoints(standing.getPoints() + 1);
            standing2.setPoints(standing2.getPoints() + 1);
        } else {
            standing = getStandingForTeam(match.getTournament(), match.getTeam2());
            standing.setPoints(standing.getPoints() + 3);
        }

        if (standing != null) {
            standingsRepository.save(standing);
        }

        if (standing2 != null) {
            standingsRepository.save(standing2);
        }
    }

    private Standing getStandingForTeam(Tournament tournament, Team team) {
        return standingsRepository.findById(
                StandingId
                        .builder()
                        .tournamentId(tournament.getId())
                        .teamId(team.getId())
                        .build()
        ).get();
    }
}
