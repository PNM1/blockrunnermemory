package com.blockrunnermemory.support;

public class Clock {
	private static PauseTimer pause = null;
	private static Clock clock = null;

	public static class PauseTimer extends com.blockrunnermemory.support.CountDownClock {
		private OnTimerCount onTimerCount = null;
		public PauseTimer(long millisOnTimer, long countDownInterval, boolean runAtStart, OnTimerCount onTimerCount) {
			super(millisOnTimer, countDownInterval, runAtStart);
			this.onTimerCount = onTimerCount;
		}
		@Override
		public void onTick(long millisUntilFinished) {
			if (onTimerCount != null) {
				onTimerCount.onTick(millisUntilFinished);
			}
		}
		@Override
		public void onFinish() {
			if (onTimerCount != null) {
				onTimerCount.onFinish();
			}
		}
	}
	public static Clock getStatus() {
		if (clock == null) {
			clock = new Clock();
		}
		return clock;
	}
	public void startTimer(long millisOnTimer, long countDownInterval, OnTimerCount onTimerCount) {
		if (pause != null) {
			pause.cancel();
		}
		pause = new PauseTimer(millisOnTimer, countDownInterval, true, onTimerCount);
		pause.create();
	}
	public void pause() {
		if (pause != null) {
			pause.pause();
		}
	}
	public long getPassedTime() { return pause.timePassed();	}

}
