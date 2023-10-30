package roksard.typingTrainer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import roksard.typingTrainer.pojo.Statistic;

@Component
public class Session {
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public UpperPanel getUpperPanel() {
		return upperPanel;
	}

	public void setUpperPanel(UpperPanel upperPanel) {
		this.upperPanel = upperPanel;
	}

	public Statistic getCurrentStats() {
		return currentStats;
	}

	public void setCurrentStats(Statistic currentStats) {
		this.currentStats = currentStats;
	}

	public long getStatisticListCountSum() {
		return statisticListCountSum;
	}

	public void setStatisticListCountSum(long statisticListCountSum) {
		this.statisticListCountSum = statisticListCountSum;
	}

	public List<Statistic> getStatisticList() {
		return statisticList;
	}

	public void setStatisticList(List<Statistic> statisticList) {
		this.statisticList = statisticList;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean started) {
		isStarted = started;
	}

	public Instant getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(Instant startedTime) {
		this.startedTime = startedTime;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Deque<Long> getMomentarySpeedLettersTimeList() {
		return momentarySpeedLettersTimeList;
	}

	public long getMomentarySpeedRange() {
		return momentarySpeedRange;
	}

	private Logger logger = LogManager.getLogger(this.getClass());

	public static enum VALUE {
		UNDEFINED(-1);

		public long getLongV() {
			return longV;
		}

		long longV;

		VALUE(long longV) {
			this.longV = longV;
		}
	}

	@Autowired
	private UpperPanel upperPanel;

	private Statistic currentStats = new Statistic();
	private long statisticListCountSum = VALUE.UNDEFINED.getLongV();
	private List<Statistic> statisticList = new LinkedList<>();
	private boolean isStarted = false;
	private Instant startedTime;
	private Timer timer;
	private final Deque<Long> momentarySpeedLettersTimeList = new LinkedBlockingDeque<>(); // used to calculate
																							 // momentary typing speed
																							 // in last N seconds
	private final long momentarySpeedRange = 5000; // (ms) in what period is momentary typing speed calculated

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
		synchronized (momentarySpeedLettersTimeList) {
			momentarySpeedLettersTimeList.clear();
		}
	}

	public long calcTotalTypedCount() {
		if (statisticListCountSum == VALUE.UNDEFINED.getLongV()) {
			statisticListCountSum = statisticList.stream().map(Statistic::getCount).reduce(Math::addExact)
					.orElseThrow(() -> new RuntimeException("could not sum up longs"));
		}
		// use prev calculated value of statisticListCountSum if it was already
		// calculated
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
		removeOldLetterTimes();
		synchronized (momentarySpeedLettersTimeList) {
			if (momentarySpeedLettersTimeList.isEmpty()) {
				return 0;
			}
			return momentarySpeedLettersTimeList.size() / (double) momentarySpeedRange * 1000 * 60;
		}
	}

	public void removeOldLetterTimes() {
		boolean checkList = true;
		synchronized (momentarySpeedLettersTimeList) {
			while (checkList && !momentarySpeedLettersTimeList.isEmpty()) {
				if (System.currentTimeMillis() - momentarySpeedLettersTimeList.getLast() > momentarySpeedRange) {
					momentarySpeedLettersTimeList.pollLast();
				} else {
					checkList = false;
				}
			}
		}
	}

}
