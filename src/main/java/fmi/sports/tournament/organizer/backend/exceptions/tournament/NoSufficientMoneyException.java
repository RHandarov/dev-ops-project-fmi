package fmi.sports.tournament.organizer.backend.exceptions.tournament;

public class NoSufficientMoneyException extends RuntimeException {
    public NoSufficientMoneyException(String message) {
        super(message);
    }
}
