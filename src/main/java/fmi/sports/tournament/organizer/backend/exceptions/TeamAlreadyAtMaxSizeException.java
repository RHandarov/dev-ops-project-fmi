package fmi.sports.tournament.organizer.backend.exceptions;

public class TeamAlreadyAtMaxSizeException extends RuntimeException {

    public TeamAlreadyAtMaxSizeException(String message) {
        super(message);
    }

    public TeamAlreadyAtMaxSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
