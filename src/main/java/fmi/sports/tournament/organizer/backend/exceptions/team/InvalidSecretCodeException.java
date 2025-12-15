package fmi.sports.tournament.organizer.backend.exceptions.team;

public class InvalidSecretCodeException extends RuntimeException {
    public InvalidSecretCodeException(String message) {
        super(message);
    }
    public InvalidSecretCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
