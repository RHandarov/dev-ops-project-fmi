package fmi.sports.tournament.organizer.backend.exceptions.team;

public class UserAlreadyRegisteredForAnotherTeamException extends RuntimeException {
    public UserAlreadyRegisteredForAnotherTeamException(String msg) {
        super(msg);
    }
}
