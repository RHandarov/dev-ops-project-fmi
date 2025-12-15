package fmi.sports.tournament.organizer.backend.repositories;

import fmi.sports.tournament.organizer.backend.entities.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRepository extends JpaRepository<Token, Long> {
}
