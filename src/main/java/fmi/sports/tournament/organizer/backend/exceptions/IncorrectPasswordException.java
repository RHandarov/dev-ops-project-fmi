package fmi.sports.tournament.organizer.backend.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("User's password is incorrect!");
    }
}
