package cannon.jackson;

import lombok.*;
import java.time.Duration;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerData {
    @NonNull
    private String playerName;
    private boolean solved;
    private int moves;
    private Duration duration;
    private ZonedDateTime created;
}
