package roksard.typingTrainer;

import lombok.Getter;
import lombok.Setter;
import roksard.typingTrainer.pojo.Statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Timer;

@Getter
@Setter
public class Session {
    private Statistic currentStats = new Statistic();
    private boolean isStarted = false;
    private Timer timer;

    public long calcCurrentTime() {
        return getCurrentStats().getTimeMs()
                + getCurrentStats().getStartTime().until(Instant.now(), ChronoUnit.MILLIS);
    }

    public String formatTimeMs(long timeMs) {
        int seconds = (int)Math.round(timeMs / 1000.0);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        minutes = minutes % 60;
        seconds = seconds % 60;
        StringBuilder result = new StringBuilder();
        result.insert(0, String.format("%02d", seconds));
        if (minutes > 0 || hours > 0) {
            result.insert(0, String.format("%02d:", minutes));
        }
        if (hours > 0) {
            result.insert(0, String.format("%02d:", hours));
        }
        return result.toString();
    }

}
