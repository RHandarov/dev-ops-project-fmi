package fmi.sports.tournament.organizer.backend.entities.team;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantId implements Serializable {
  private Long user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ParticipantId that)) return false;
    return user != null && user.equals(that.user);
  }

  @Override
  public int hashCode() {
    return user != null ? user.hashCode() : 0;
  }
}
