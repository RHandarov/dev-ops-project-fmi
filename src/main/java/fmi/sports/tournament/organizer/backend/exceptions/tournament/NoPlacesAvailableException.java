package fmi.sports.tournament.organizer.backend.exceptions.tournament;

public class NoPlacesAvailableException extends RuntimeException {
    public NoPlacesAvailableException(String message) {
        super(message);
    }
}
