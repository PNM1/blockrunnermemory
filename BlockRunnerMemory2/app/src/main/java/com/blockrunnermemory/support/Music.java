package com.blockrunnermemory.support;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.blockrunnermemory.R;
import com.blockrunnermemory.states.State;


public class Music {

	public static boolean off = false;
	public static void playSound() {
		if (!off) {
			MediaPlayer mediaPlayer = MediaPlayer.create(State.context, R.raw.correct_answer);
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mediaPlayer) {
					mediaPlayer.reset();
					mediaPlayer.release();
					mediaPlayer = null;
				}
			});	mediaPlayer.start();
		}
	}
	public static void playStar() {
		if (!off) {
			MediaPlayer mediaPlayer = MediaPlayer.create(State.context, R.raw.star);
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mediaPlayer) {
					mediaPlayer.reset();
					mediaPlayer.release();
					mediaPlayer = null;
				}
			});	mediaPlayer.start();
		}
	}
	public static void playBgs() {
		MediaPlayer mediaPlayer = MediaPlayer.create(State.context, R.raw.background_music);
		mediaPlayer.setLooping(true);
	}
}
