package fmi.sports.tournament.organizer.backend.exceptions;

public class NoPlacesAvailableException extends RuntimeException {
    public NoPlacesAvailableException(String message) {
        super(message);
    }
}
