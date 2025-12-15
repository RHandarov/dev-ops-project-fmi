package fmi.sports.tournament.organizer.backend.exceptions;

public class NoParticipantWithIdException extends RuntimeException {
    public NoParticipantWithIdException(Long participantId) {
        super(String.format("User with id %d doesn't participate in this team!", participantId));
    }
}
