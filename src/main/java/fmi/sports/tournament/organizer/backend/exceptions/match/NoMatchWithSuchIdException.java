package fmi.sports.tournament.organizer.backend.exceptions.match;

public class NoMatchWithSuchIdException extends RuntimeException {
    public NoMatchWithSuchIdException(String message) {
        super(message);
    }
}
