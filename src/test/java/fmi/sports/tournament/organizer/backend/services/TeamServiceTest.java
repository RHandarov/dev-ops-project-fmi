package fmi.sports.tournament.organizer.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fmi.sports.tournament.organizer.backend.dtos.TeamDTO;
import fmi.sports.tournament.organizer.backend.entities.team.Team;
import fmi.sports.tournament.organizer.backend.repositories.TeamsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {
  @Mock private TeamsRepository teamRepository;

  @InjectMocks private TeamServiceImpl teamService;

  @Test
  @DisplayName("Test creating a new team")
  void createTeam_ShouldSave() {
    Team savedTeam =
        new Team(
            10L,
            "New Challengers",
            "some@example.com",
            2500.0,
            12,
            "some_secret",
            null,
            null,
            null);
    TeamDTO teamDto = TeamDTO.fromEntity(savedTeam);

    when(teamRepository.save(any(Team.class))).thenReturn(savedTeam);

    TeamDTO result = teamService.create(teamDto);

    assertEquals(10L, result.getId());
    verify(teamRepository).save(any(Team.class));
  }
}
