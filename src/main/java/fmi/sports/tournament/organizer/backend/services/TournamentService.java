package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.StandingDTO;
import fmi.sports.tournament.organizer.backend.dtos.TeamDTO;
import fmi.sports.tournament.organizer.backend.dtos.TournamentDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TournamentService {
  TournamentDTO create(TournamentDTO newTournament);

  List<TournamentDTO> getAll();

  TournamentDTO getById(Long tournamentId);

  void updateById(TournamentDTO updatedTournament, long id);

  TournamentDTO deleteById(Long tournamentId);

  List<TeamDTO> getAllParticipatingTeams(Long tournamentId);

  void registerTeamForParticipation(Long tournamentId, Long teamId);

  void unregisterTeamForParticipation(Long tournamentId, Long teamId);

  List<StandingDTO> getTournamentStandings(Long tournamentId);
}
