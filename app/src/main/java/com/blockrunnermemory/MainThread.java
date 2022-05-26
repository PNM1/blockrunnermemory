package com.blockrunnermemory;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ImageView;

import com.blockrunnermemory.connectevent.GameObjectAdapter;
import com.blockrunnermemory.event.BackPoly;
import com.blockrunnermemory.event.SelectDifficult;
import com.blockrunnermemory.event.FlipCard;
import com.blockrunnermemory.event.FlipDown;
import com.blockrunnermemory.event.WinEvent;
import com.blockrunnermemory.event.Hide;
import com.blockrunnermemory.event.NextPoly;
import com.blockrunnermemory.event.ToBgs;
import com.blockrunnermemory.event.Start;
import com.blockrunnermemory.event.SelectTheme;
import com.blockrunnermemory.states.Board;
import com.blockrunnermemory.states.Cfg;
import com.blockrunnermemory.states.Play;
import com.blockrunnermemory.states.PlayState;
import com.blockrunnermemory.states.State;
import com.blockrunnermemory.support.Cache;
import com.blockrunnermemory.support.Clock;
import com.blockrunnermemory.support.Music;
import com.blockrunnermemory.support.Util;
import com.blockrunnermemory.themeselect.ThemeState;
import com.blockrunnermemory.themeselect.Themes;
import com.blockrunnermemory.userinterface.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainThread extends GameObjectAdapter {

	private static MainThread mainThread = null;
	private Play isPlaying = null;

	private ScreenGameManager screenGameManager;
	private ThemeState themeState;
	private ImageView bgs;
	private Handler handler;

	private MainThread() {
		screenGameManager = ScreenGameManager.getStatus();
		handler = new Handler();
	}

	public static MainThread getStatus() {
		if (mainThread == null) {
			mainThread = new MainThread();
		}
		return mainThread;
	}

	public void start() {
		State.eventBus.play(SelectDifficult.NAME, this);
		State.eventBus.play(FlipCard.NAME, this);
		State.eventBus.play(Start.NAME, this);
		State.eventBus.play(SelectTheme.NAME, this);
		State.eventBus.play(BackPoly.NAME, this);
		State.eventBus.play(NextPoly.NAME, this);
		State.eventBus.play(ToBgs.NAME, this);
	}

	public void stop() {
		isPlaying = null;
		bgs.setImageDrawable(null);
		bgs = null;
		handler.removeCallbacksAndMessages(null);
		handler = null;

		State.eventBus.dontplay(SelectDifficult.NAME, this);
		State.eventBus.dontplay(FlipCard.NAME, this);
		State.eventBus.dontplay(Start.NAME, this);
		State.eventBus.dontplay(SelectTheme.NAME, this);
		State.eventBus.dontplay(BackPoly.NAME, this);
		State.eventBus.dontplay(NextPoly.NAME, this);
		State.eventBus.dontplay(ToBgs.NAME, this);

		mainThread = null;
	}

	@Override
	public void onEvent(ToBgs toBgs) {
		Drawable drawable = bgs.getDrawable();
		if (drawable != null) {
			((TransitionDrawable) drawable).reverseTransition(2000);
		} else {
			new AsyncTask<Void, Void, Bitmap>() {
				@Override
				protected Bitmap doInBackground(Void... params) {
					Bitmap bitmap = Util.scaleDown(R.drawable.background, Util.wWidth(), Util.wHeight());
					return bitmap;
				}
				protected void onPostExecute(Bitmap bitmap) {bgs.setImageBitmap(bitmap);};
			}.execute();
		}
	}

	@Override
	public void onEvent(Start start) {
		screenGameManager.openScreen(ScreenGameManager.Screen.SELECTTHEME);
	}

	@Override
	public void onEvent(NextPoly nextPoly) {
		WindowManager.closeWinup();
		int difficulty = isPlaying.cfg.difficult;
		if (isPlaying.playState.achievedStars == 3 && difficulty < 6) {
			difficulty++;
		}
		State.eventBus.call(new SelectDifficult(difficulty));
	}

	@Override
	public void onEvent(BackPoly backPoly) {
		WindowManager.closeWinup();
		screenGameManager.openScreen(ScreenGameManager.Screen.SELECTDIFFICULT);
	}

	@Override
	public void onEvent(SelectTheme selectTheme) {
		themeState = selectTheme.themeState;
		screenGameManager.openScreen(ScreenGameManager.Screen.SELECTDIFFICULT);
		AsyncTask<Void, Void, TransitionDrawable> task = new AsyncTask<Void, Void, TransitionDrawable>() {
			@Override
			protected TransitionDrawable doInBackground(Void... params) {
				Bitmap bitmap = Util.scaleDown(R.drawable.background, Util.wWidth(), Util.wHeight());
				Bitmap image = Themes.getBackgroundImage(themeState);
				image = Util.crop(image, Util.wHeight(), Util.wWidth());
				Drawable bgs[] = new Drawable[2];
				bgs[0] = new BitmapDrawable(State.context.getResources(), bitmap);
				bgs[1] = new BitmapDrawable(State.context.getResources(), image);
				TransitionDrawable fade = new TransitionDrawable(bgs);
				return fade;
			}
			@Override
			protected void onPostExecute(TransitionDrawable result) {
				super.onPostExecute(result);
				bgs.setImageDrawable(result);
				result.startTransition(2000);
			}
		};		task.execute();
	}

	private int flipId = -1;
	private int toFlip = -1;

	@Override
	public void onEvent(SelectDifficult selectDifficult) {
		flipId = -1;
		isPlaying = new Play();
		isPlaying.cfg = new Cfg(selectDifficult.difficult);
		isPlaying.themeState = themeState;
		toFlip = isPlaying.cfg.num;
		arrangeBoard();
		screenGameManager.openScreen(ScreenGameManager.Screen.PLAY);
	}

	private void arrangeBoard() {
		Cfg cfg = isPlaying.cfg;
		Board board = new Board();
		List<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < cfg.num; i++) {
			ids.add(i);
		}
		Collections.shuffle(ids);
		List<String> tileImageUrls = isPlaying.themeState.tileImage;
		Collections.shuffle(tileImageUrls);
		board.pairs = new HashMap<Integer, Integer>();
		board.urla = new HashMap<Integer, String>();
		int j = 0;
		for (int i = 0; i < ids.size(); i++) {
			if (i + 1 < ids.size()) {
				board.pairs.put(ids.get(i), ids.get(i + 1));
				board.pairs.put(ids.get(i + 1), ids.get(i));
				board.urla.put(ids.get(i), tileImageUrls.get(j));
				board.urla.put(ids.get(i + 1), tileImageUrls.get(j));
				i++;
				j++;
			}
		}

		isPlaying.board = board;
	}





	@Override
	public void onEvent(FlipCard flipCard) {
		int id = flipCard.x;
		if (flipId == -1) {
			flipId = id;
		} else {
			if (isPlaying.board.isPair(flipId, id)) {
				State.eventBus.call(new Hide(flipId, id), 1000);
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						Music.playSound();
					}
				}, 1000);
				toFlip -= 2;
				if (toFlip == 0) {
					int passedSeconds = (int) (Clock.getStatus().getPassedTime() / 1000);
					Clock.getStatus().pause();
					int totalTime = isPlaying.cfg.time;
					PlayState playState = new PlayState();
					isPlaying.playState = playState;
					playState.remainedSeconds = totalTime - passedSeconds;
					playState.passedSeconds = passedSeconds;
					if (passedSeconds <= totalTime / 2) {
						playState.achievedStars = 3;
					} else if (passedSeconds <= totalTime - totalTime / 5) {
						playState.achievedStars = 2;
					} else if (passedSeconds < totalTime) {
						playState.achievedStars = 1;
					} else {
						playState.achievedStars = 0;
					}
					playState.achievedScore = isPlaying.cfg.difficult * playState.remainedSeconds * isPlaying.themeState.dx;
					Cache.saveStar(isPlaying.themeState.dx, isPlaying.cfg.difficult, playState.achievedStars);
					Cache.saveTime(isPlaying.themeState.dx, isPlaying.cfg.difficult, playState.passedSeconds);

					Cache.getBDTime(isPlaying.themeState.dx, isPlaying.cfg.difficult, playState.passedSeconds);
					Cache.getBDStar(isPlaying.themeState.dx, isPlaying.cfg.difficult, playState.passedSeconds);




					State.eventBus.call(new WinEvent(playState), 1200);
				}
			} else {
				State.eventBus.call(new FlipDown(), 1000);
			}
			flipId = -1;
		}
	}

	public Play getActiveGame() {
		return isPlaying;
	}

	public ThemeState getSelectedTheme() {
		return themeState;
	}

	public void setBackgroundImageView(ImageView backgroundImage) {
		bgs = backgroundImage;
	}
}
