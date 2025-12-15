package fmi.sports.tournament.organizer.backend.exceptions.user.auth;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("User's password is incorrect!");
    }
}
