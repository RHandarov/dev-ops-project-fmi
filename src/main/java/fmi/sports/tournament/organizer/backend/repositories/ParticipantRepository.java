package fmi.sports.tournament.organizer.backend.repositories;

import fmi.sports.tournament.organizer.backend.entities.team.Participant;
import fmi.sports.tournament.organizer.backend.entities.team.ParticipantId;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import jakarta.servlet.http.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends CrudRepository<Participant, User> {

    void deleteByTeamId(Long TeamId);
    Optional<Participant> findByUserId(Long UserId);
    List<Participant> findAllByTeamId(Long teamId);
    Optional<Participant> findByTeamIdAndUserId(Long teamId, Long userId);
}
