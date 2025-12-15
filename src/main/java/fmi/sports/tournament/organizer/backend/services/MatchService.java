package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.MatchDTO;
import fmi.sports.tournament.organizer.backend.dtos.MatchResultsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchService {
    MatchDTO schedule(MatchDTO newMatch);
    List<MatchDTO> getAllForTournament(Long tournamentId);
    MatchDTO getById(Long matchId);
    MatchDTO deleteById(Long matchId);
    MatchDTO updateResults(Long matchId, MatchResultsDTO newResults);
    MatchDTO markAsFinished(Long matchId);
}
