package fmi.sports.tournament.organizer.backend.exceptions.user.auth;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
