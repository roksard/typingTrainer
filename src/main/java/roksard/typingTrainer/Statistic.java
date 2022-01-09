package roksard.typingTrainer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    private Instant sessionStart;
    private Instant sessionEnd;
    private Long count;
    private Long errCount;
}
