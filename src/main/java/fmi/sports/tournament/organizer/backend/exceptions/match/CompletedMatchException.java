package fmi.sports.tournament.organizer.backend.exceptions.match;

public class CompletedMatchException extends RuntimeException {
    public CompletedMatchException(Long matchId) {
        super("Match with id " + matchId + " is already completed!");
    }
}
