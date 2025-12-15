package fmi.sports.tournament.organizer.backend.exceptions.user;

public class NoUserWithSuchIdException extends RuntimeException {
    public NoUserWithSuchIdException(Long userId) {
        this(String.format("User with id %d does not exist", userId));
    }

    public NoUserWithSuchIdException(String msg) {
        super(msg);
    }

    public NoUserWithSuchIdException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
