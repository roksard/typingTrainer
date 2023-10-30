package roksard.typingTrainer.pojo;

import java.time.Instant;

public class Statistic {
	public Long getCreatedEpoch() {
		return createdEpoch;
	}

	public void setCreatedEpoch(Long createdEpoch) {
		this.createdEpoch = createdEpoch;
	}

	public Long getTimeMs() {
		return timeMs;
	}

	public void setTimeMs(Long timeMs) {
		this.timeMs = timeMs;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getErrCount() {
		return errCount;
	}

	public void setErrCount(Long errCount) {
		this.errCount = errCount;
	}

	private Long createdEpoch = Instant.now().toEpochMilli();
	private Long timeMs = 0L;
	private Long count = 0L;
	private Long errCount = 0L;
}
