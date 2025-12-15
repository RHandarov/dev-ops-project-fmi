package fmi.sports.tournament.organizer.backend.exceptions;

public class UserNotInTeamException extends RuntimeException {
    public UserNotInTeamException(String msg) {
        super(msg);
    }

    public UserNotInTeamException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
