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
    private MainJPanel mainJPanel;

    public long calcCurrentTime() {
        if (getCurrentStats().getStartTime() == null) {
            return 0;
        }
        return getCurrentStats().getTimeMs()
                + getCurrentStats().getStartTime().until(Instant.now(), ChronoUnit.MILLIS);
    }

    public void resetCurrentStats() {
        currentStats = new Statistic();
        currentStats.setStartTime(Instant.now());
        mainJPanel.resetAllLabels();
    }


}
