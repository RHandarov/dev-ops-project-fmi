package fmi.sports.tournament.organizer.backend.exceptions.team;

public class TeamNotParticipatingException extends RuntimeException {
    public TeamNotParticipatingException(Long teamId) {
        super("Team with id " + teamId + " is not participating in this tournament!");
    }
}
