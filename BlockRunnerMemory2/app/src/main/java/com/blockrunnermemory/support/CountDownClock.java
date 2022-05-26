package com.blockrunnermemory.support;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

public abstract class CountDownClock {

	private long stopTime;
	private long millisecs;
	private final long total;
	private final long interval;
	private long pause;
	private boolean run;

	public CountDownClock(long millisecsOnTimer, long countDownInterval, boolean runAtStart) {
		millisecs = millisecsOnTimer;
		total = millisecs;
		interval = countDownInterval;
		run = runAtStart;
	}
	public final void cancel() {		handler.removeCallbacksAndMessages(null);	}

	public void resume() {
		if (isPaused()) {
			millisecs = pause;
			stopTime = SystemClock.elapsedRealtime() + millisecs;
			handler.sendMessage(handler.obtainMessage(Message));
			pause = 0;
		}
	}

	public synchronized final CountDownClock create() {
		if (millisecs <= 0) {
			onFinish();
		} else {
			pause = millisecs;
		}
		if (run) {			resume();		}
		return this;
	}

	public void pause() {
		if (isRunning()) {
			pause = leftTime();
			cancel();
		}
	}

	public boolean isPaused() {		return (pause > 0);	}

	public boolean isRunning() {		return (!isPaused());	}

	public long leftTime() {
		long millisUntilFinished;
		if (isPaused()) {
			millisUntilFinished = pause;
		} else {
			millisUntilFinished = stopTime - SystemClock.elapsedRealtime();
			if (millisUntilFinished < 0)
				millisUntilFinished = 0;
		}
		return millisUntilFinished;
	}

	public long timePassed() {		return total - leftTime();	}

	public abstract void onTick(long millisUntilFinished);

	public abstract void onFinish();

	private static final int Message = 1;

	private final Handler handler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message message) {
			synchronized (CountDownClock.this) {
				long leftMilliseconds = leftTime();
				if (leftMilliseconds <= 0) {
					cancel();
					onFinish();
				} else if (leftMilliseconds < interval) {
					sendMessageDelayed(obtainMessage(CountDownClock.Message), leftMilliseconds);
				} else {
					long lastTickStart = SystemClock.elapsedRealtime();
					onTick(leftMilliseconds);
					long delay = interval - (SystemClock.elapsedRealtime() - lastTickStart);
					while (delay < 0)
						delay += interval;

					sendMessageDelayed(obtainMessage(CountDownClock.Message), delay);
				}			}		}	};
}
