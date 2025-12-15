package fmi.sports.tournament.organizer.backend.exceptions.user;

public class NoUserWithSuchEmailException extends RuntimeException {
    public NoUserWithSuchEmailException(String email) {
        super(String.format("User with email %s doesn't exists!", email));
    }
}
