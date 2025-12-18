package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.MatchResultsDTO;
import fmi.sports.tournament.organizer.backend.entities.team.Team;
import fmi.sports.tournament.organizer.backend.entities.tournament.Standing;
import fmi.sports.tournament.organizer.backend.entities.tournament.StandingId;
import fmi.sports.tournament.organizer.backend.entities.tournament.Tournament;
import fmi.sports.tournament.organizer.backend.entities.tournament.match.*;
import fmi.sports.tournament.organizer.backend.repositories.MatchesRepository;
import fmi.sports.tournament.organizer.backend.repositories.StandingsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {
    @Mock
    private MatchesRepository matchRepository;

    @Mock
    private StandingsRepository standingsRepository;

    @InjectMocks
    private MatchServiceImpl matchService;

    @Test
    @DisplayName("Update match results and mark match as finished")
    void updateMatchResult_ShouldUpdateScoreAndStatus() {
        Team mockTeam1 = new Team(1L,
                "Team 1",
                "some@example.com",
                250.0,
                12,
                "some_secret",
                null,
                null,
                null);
        Team mockTeam2 = new Team(2L,
                "Team 2",
                "some@example.com",
                250.0,
                12,
                "some_secret",
                null,
                null,
                null);

        Tournament mockTournament =
                new Tournament(1L,
                        "Mock tournament",
                        "LA",
                        "football",
                        LocalDate.now(),
                        LocalDate.now().plusDays(12),
                        12.0,
                        12,
                        null);

        Long matchId = 5L;
        Match match = new Match();
        match.setId(matchId);
        match.setTournament(mockTournament);
        match.setTeam1(mockTeam1);
        match.setTeam2(mockTeam2);
        match.setStatus(MatchStatus.ONGOING);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(matchRepository.save(any(Match.class))).thenAnswer(i -> i.getArguments()[0]);
        when(standingsRepository.findById(new StandingId(mockTournament.getId(), mockTeam1.getId())))
                .thenReturn(Optional.of(new Standing(mockTournament.getId(),
                        mockTeam1.getId(),
                        mockTournament,
                        mockTeam1,
                        2)));

        matchService.updateResults(matchId, new MatchResultsDTO(2, 1));
        matchService.markAsFinished(matchId);

        assertEquals(2, match.getTeam1Points());
        assertEquals(1, match.getTeam2Points());
        assertEquals(MatchStatus.COMPLETED, match.getStatus());
    }
}
