package roksard.typingTrainer.pojo;

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
    private Instant startTime;
    private Long timeMs = 0L;
    private Long count = 0L;
    private Long errCount = 0L;
}
