package fmi.sports.tournament.organizer.backend.exceptions.user.auth;

public class NoSessionWithSuchIdException extends RuntimeException {
    public NoSessionWithSuchIdException(String message) {
        super(message);
    }
    public NoSessionWithSuchIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
