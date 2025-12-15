package fmi.sports.tournament.organizer.backend.exceptions;

public class NoTeamWithSuchIdException extends RuntimeException {

    public NoTeamWithSuchIdException(String msg) {
        super(msg);
    }

    public NoTeamWithSuchIdException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
