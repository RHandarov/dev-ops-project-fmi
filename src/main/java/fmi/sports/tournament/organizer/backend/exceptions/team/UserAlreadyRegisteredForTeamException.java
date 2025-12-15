package fmi.sports.tournament.organizer.backend.exceptions.team;

public class UserAlreadyRegisteredForTeamException extends RuntimeException {
    
    public UserAlreadyRegisteredForTeamException(String message) {
        super(message);
    }

    public UserAlreadyRegisteredForTeamException(String message, Throwable cause) {
        super(message, cause);
    }

}
