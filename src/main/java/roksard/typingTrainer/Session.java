package roksard.typingTrainer;

import lombok.Getter;
import lombok.Setter;
import roksard.typingTrainer.pojo.Statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

@Getter
@Setter
public class Session {

    @Getter
    public static enum VALUE {
        UNDEFINED(-1);
        long longV;
        VALUE(long longV) {
            this.longV = longV;
        }
    }

    private Statistic currentStats = new Statistic();
    private long statisticListCountSum = VALUE.UNDEFINED.getLongV();
    private List<Statistic> statisticList = new LinkedList<>();
    private boolean isStarted = false;
    private Instant startedTime;
    private Timer timer;
    private MainJPanel mainJPanel;

    public void recalcTimeMs() {
        currentStats.setTimeMs(calcCurrentRunningTime());
    }

    public long calcCurrentRunningTime() {
        if (startedTime == null) {
            return currentStats.getTimeMs();
        }
        return currentStats.getTimeMs() + startedTime.until(Instant.now(), ChronoUnit.MILLIS);
    }

    public void resetCurrentStats() {
        recalcTimeMs();
        setStartedTime(Instant.now());
        statisticList.add(0, currentStats);
        resetPrecalculatedListCountSum();
        currentStats = new Statistic();
        mainJPanel.resetAllLabels();
    }

    public long calcTotalTypedCount() {
        if (statisticListCountSum == VALUE.UNDEFINED.getLongV()) {
            statisticListCountSum = statisticList.stream()
                    .map(Statistic::getCount)
                    .reduce(Math::addExact)
                    .orElseThrow(() -> new RuntimeException("could not sum up longs"));
        }
        //use prev calculated value of statisticListCountSum if it was already calculated
        return currentStats.getCount() + statisticListCountSum;
    }

    public void resetPrecalculatedListCountSum() {
        statisticListCountSum = VALUE.UNDEFINED.getLongV();
    }

}
