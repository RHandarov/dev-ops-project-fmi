package fmi.sports.tournament.organizer.backend.repositories;

import fmi.sports.tournament.organizer.backend.entities.tournament.Standing;
import fmi.sports.tournament.organizer.backend.entities.tournament.StandingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingsRepository extends JpaRepository<Standing, StandingId> {
    List<Standing> findByTournamentId(Long tournamentId);
}
