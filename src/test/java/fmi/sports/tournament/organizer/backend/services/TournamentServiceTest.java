package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.TournamentDTO;
import fmi.sports.tournament.organizer.backend.entities.tournament.Tournament;
import fmi.sports.tournament.organizer.backend.exceptions.tournament.NoTournamentWithSuchIdException;
import fmi.sports.tournament.organizer.backend.repositories.StandingsRepository;
import fmi.sports.tournament.organizer.backend.repositories.TeamsRepository;
import fmi.sports.tournament.organizer.backend.repositories.TournamentsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentServiceTest {
    @Mock
    private TournamentsRepository tournamentsRepository;

    @Mock
    private TeamsRepository teamsRepository;

    @Mock
    private StandingsRepository standingsRepository;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    @Test
    @DisplayName("Create Tournament - Success")
    void createTournament_Success() {
        TournamentDTO inputDto = new TournamentDTO("Summer Cup", "London", "Football", LocalDate.now(), LocalDate.now().plusDays(10), 100.0, 5);
        Tournament savedTournament = Tournament.fromDTO(inputDto);

        when(tournamentsRepository.save(any(Tournament.class))).thenReturn(savedTournament);

        TournamentDTO result = tournamentService.create(inputDto);

        assertNotNull(result);
        assertEquals("Summer Cup", result.getName());
        verify(tournamentsRepository, times(1)).save(any(Tournament.class));
    }

    @Test
    @DisplayName("Get Tournament by ID - Found")
    void getTournament_Found() {
        Long tournamentId = 1L;
        Tournament tournament = new Tournament();
        tournament.setId(tournamentId);
        tournament.setName("Winter League");

        when(tournamentsRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        TournamentDTO result = tournamentService.getById(tournamentId);

        assertNotNull(result);
        assertEquals(tournamentId, result.getId());
    }

    @Test
    @DisplayName("Get Tournament by ID - Not Found")
    void getTournament_NotFound() {
        Long tournamentId = 99L;
        when(tournamentsRepository.findById(tournamentId)).thenReturn(Optional.empty());

        assertThrows(NoTournamentWithSuchIdException.class, () -> tournamentService.getById(tournamentId));
    }
}
