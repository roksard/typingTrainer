package roksard.typingTrainer;

import lombok.Getter;
import lombok.Setter;
import roksard.typingTrainer.pojo.Statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

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
    private UpperPanel upperPanel;
    private final Deque<Long> momentarySpeedLettersTimeList = new LinkedBlockingDeque<>(); //used to calculate momentary typing speed in last N seconds
    private final long momentarySpeedRange = 30000; //(ms) in what period is momentary typing speed calculated

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
        upperPanel.resetAllLabels();
        resetMomentarySpeed();
    }

    public void resetMomentarySpeed() {
        momentarySpeedLettersTimeList.clear();
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

    public double calcAverageTypingSpeed() {
        double average = 0;
        long timeMillis = calcCurrentRunningTime();
        if (timeMillis != 0) {
            double timeMinutes = ((double) timeMillis) / (1000 * 60);
            average = currentStats.getCount() / timeMinutes;
        }
        return average;
    }

    public double calcMomentaryTypingSpeed() {
        if (momentarySpeedLettersTimeList.isEmpty()) {
            return 0;
        }
        long last = momentarySpeedLettersTimeList.peekLast();
        long delta = System.currentTimeMillis() - last;
        if (delta > momentarySpeedRange) {
            delta = momentarySpeedRange;
        }
        return momentarySpeedLettersTimeList.size() / ((double)delta/1000.0) * 60;
    }

    public void removeOldLetterTimes() {
        boolean checkList = true;
        while (checkList && !momentarySpeedLettersTimeList.isEmpty()) {
            if (System.currentTimeMillis() - momentarySpeedLettersTimeList.getLast() > momentarySpeedRange) {
                momentarySpeedLettersTimeList.pollLast();
            } else {
                checkList = false;
            }
        }
    }

}
