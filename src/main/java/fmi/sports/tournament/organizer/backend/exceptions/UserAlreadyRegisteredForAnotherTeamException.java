package fmi.sports.tournament.organizer.backend.exceptions;

public class UserAlreadyRegisteredForAnotherTeamException extends RuntimeException {
    public UserAlreadyRegisteredForAnotherTeamException(String msg) {
        super(msg);
    }
}
