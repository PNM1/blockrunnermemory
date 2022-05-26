package com.blockrunnermemory.ViewConrol;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockrunnermemory.R;
import com.blockrunnermemory.event.FlipDown;
import com.blockrunnermemory.event.WinEvent;
import com.blockrunnermemory.event.Hide;
import com.blockrunnermemory.states.Play;
import com.blockrunnermemory.states.State;
import com.blockrunnermemory.support.Clock;
import com.blockrunnermemory.support.FontLoader;
import com.blockrunnermemory.support.OnTimerCount;
import com.blockrunnermemory.userinterface.BoardView;
import com.blockrunnermemory.userinterface.WindowManager;

public class GameProcess extends Main {

	private BoardView _boardView;
	private TextView _time;
	private ImageView _image;

	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle statusState) {
		ViewGroup view = (ViewGroup) layoutInflater.inflate(R.layout.game_fragment, container, false);
		view.setClipChildren(false);
		((ViewGroup)view.findViewById(R.id.game_board)).setClipChildren(false);
		_time = (TextView) view.findViewById(R.id.time_bar_text);
		_image = (ImageView) view.findViewById(R.id.time_bar_image);
		FontLoader.setTypeface(State.context, new TextView[] {
				_time		}, FontLoader.Font.ObelixPro);
		_boardView = BoardView.fromXml(getActivity().getApplicationContext(), view);
		FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.game_container);
		frameLayout.addView(_boardView);
		frameLayout.setClipChildren(false);
		buildBoard();
		State.eventBus.play(FlipDown.NAME, this);
		State.eventBus.play(Hide.NAME, this);
		State.eventBus.play(WinEvent.NAME, this);
		
		return view;
	}
	
	@Override
	public void onDestroy() {
		State.eventBus.dontplay(FlipDown.NAME, this);
		State.eventBus.dontplay(Hide.NAME, this);
		State.eventBus.dontplay(WinEvent.NAME, this);
		super.onDestroy();
	}

	private void buildBoard() {
		Play play = State.mainThread.getActiveGame();
		int time = play.cfg.time;
		setTime(time);
		_boardView.setBoard(play);
		
		startClock(time);
	}
	
	private void setTime(int time) {
		int minutes = time / 60;
		int seconds = time - minutes*60;
		_time.setText(" " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
	}

	private void startClock(int sec) {
		Clock clock = Clock.getStatus();
		clock.startTimer(sec*1000, 1000, new OnTimerCount() {
			
			@Override
			public void onTick(long milliseconds) {
				setTime((int) (milliseconds /1000));
			}
			
			@Override
			public void onFinish() {
				setTime(0);
			}
		});
	}

	@Override
	public void onEvent(WinEvent winEvent) {
		_time.setVisibility(View.GONE);
		_image.setVisibility(View.GONE);
		WindowManager.showPopupWon(winEvent.playState);
	}

	@Override
	public void onEvent(FlipDown flipDown) {
		_boardView.flipDownAll();
	}

	@Override
	public void onEvent(Hide hide) {
		_boardView.hideCards(hide.x, hide.y);
	}

}
