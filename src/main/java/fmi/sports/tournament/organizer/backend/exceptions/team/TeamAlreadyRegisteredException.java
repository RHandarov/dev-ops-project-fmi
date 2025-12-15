package fmi.sports.tournament.organizer.backend.exceptions.team;

public class TeamAlreadyRegisteredException extends RuntimeException{

    public TeamAlreadyRegisteredException(String msg) {
        super(msg);
    }
    public TeamAlreadyRegisteredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
