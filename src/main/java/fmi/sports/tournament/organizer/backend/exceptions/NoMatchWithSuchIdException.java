package fmi.sports.tournament.organizer.backend.exceptions;

public class NoMatchWithSuchIdException extends RuntimeException {
    public NoMatchWithSuchIdException(String message) {
        super(message);
    }
}
