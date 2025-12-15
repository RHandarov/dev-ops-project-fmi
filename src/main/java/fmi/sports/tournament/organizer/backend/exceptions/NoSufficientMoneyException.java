package fmi.sports.tournament.organizer.backend.exceptions;

public class NoSufficientMoneyException extends RuntimeException {
    public NoSufficientMoneyException(String message) {
        super(message);
    }
}
