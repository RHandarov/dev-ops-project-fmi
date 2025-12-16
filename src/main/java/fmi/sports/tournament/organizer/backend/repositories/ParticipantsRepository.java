package fmi.sports.tournament.organizer.backend.repositories;

import fmi.sports.tournament.organizer.backend.entities.team.Participant;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepository extends CrudRepository<Participant, User> {
  void deleteByTeamId(Long TeamId);

  Optional<Participant> findByUserId(Long UserId);

  List<Participant> findAllByTeamId(Long teamId);

  Optional<Participant> findByTeamIdAndUserId(Long teamId, Long userId);
}
