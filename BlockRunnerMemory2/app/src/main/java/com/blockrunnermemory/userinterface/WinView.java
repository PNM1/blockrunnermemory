package com.blockrunnermemory.userinterface;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockrunnermemory.R;
import com.blockrunnermemory.event.BackPoly;
import com.blockrunnermemory.event.NextPoly;
import com.blockrunnermemory.states.PlayState;
import com.blockrunnermemory.states.State;
import com.blockrunnermemory.support.Clock;
import com.blockrunnermemory.support.FontLoader;
import com.blockrunnermemory.support.Music;
import com.blockrunnermemory.support.OnTimerCount;

public class WinView extends RelativeLayout {

	private TextView _time;
	private TextView _score;
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private ImageView nextBtn;
	private ImageView backBtn;
	private Handler mHandler;

	public WinView(Context context) {
		this(context, null);
	}

	public WinView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.winup_win_view, this, true);
		_time = (TextView) findViewById(R.id.time_bar_text);
		_score = (TextView) findViewById(R.id.score_bar_text);
		imageView1 = (ImageView) findViewById(R.id.star_1);
		imageView2 = (ImageView) findViewById(R.id.star_2);
		imageView3 = (ImageView) findViewById(R.id.star_3);
		backBtn = (ImageView) findViewById(R.id.button_back);
		nextBtn = (ImageView) findViewById(R.id.button_next);
		FontLoader.setTypeface(context, new TextView[] { _time, _score }, FontLoader.Font.ObelixPro);
		setBackgroundResource(R.drawable.level_complete);
		mHandler = new Handler();

		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				State.eventBus.call(new BackPoly());
			}
		});

		nextBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				State.eventBus.call(new NextPoly());
			}
		});
	}

	public void setGameState(final PlayState playState) {
		int min = playState.remainedSeconds / 60;
		int sec = playState.remainedSeconds - min * 60;
		_time.setText(" " + String.format("%02d", min) + ":" + String.format("%02d", sec));
		_score.setText("" + 0);
		
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				animateScoreAndTime(playState.remainedSeconds, playState.achievedScore);
				animateStars(playState.achievedStars);
			}
		}, 500);
	}

	private void animateStars(int start) {
		switch (start) {
		case 0:
			imageView1.setVisibility(View.GONE);
			imageView2.setVisibility(View.GONE);
			imageView3.setVisibility(View.GONE);
			break;
		case 1:
			imageView2.setVisibility(View.GONE);
			imageView3.setVisibility(View.GONE);
			imageView1.setAlpha(0f);
			animateStar(imageView1, 0);
			break;
		case 2:
			imageView3.setVisibility(View.GONE);
			imageView1.setVisibility(View.VISIBLE);
			imageView1.setAlpha(0f);
			animateStar(imageView1, 0);
			imageView2.setVisibility(View.VISIBLE);
			imageView2.setAlpha(0f);
			animateStar(imageView2, 600);
			break;
		case 3:
			imageView1.setVisibility(View.VISIBLE);
			imageView1.setAlpha(0f);
			animateStar(imageView1, 0);
			imageView2.setVisibility(View.VISIBLE);
			imageView2.setAlpha(0f);
			animateStar(imageView2, 600);
			imageView3.setVisibility(View.VISIBLE);
			imageView3.setAlpha(0f);
			animateStar(imageView3, 1200);
			break;
		default:
			break;
		}
	}
	
	private void animateStar(final View view, int delay) {
		ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);
		alpha.setDuration(100);
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0, 1f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0, 1f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(alpha, scaleX, scaleY);
		animatorSet.setInterpolator(new BounceInterpolator());
		animatorSet.setStartDelay(delay);
		animatorSet.setDuration(600);
		view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animatorSet.start();
		
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Music.playStar();
			}
		}, delay);
	}

	private void animateScoreAndTime(final int remainedSeconds, final int achievedScore) {
		final int totalAnimation = 1200;

		Clock.getStatus().startTimer(totalAnimation, 35, new OnTimerCount() {

			@Override
			public void onTick(long milliseconds) {
				float factor = milliseconds / (totalAnimation * 1f);
				int scoreToShow = achievedScore - (int) (achievedScore * factor);
				int timeToShow = (int) (remainedSeconds * factor);
				int min = timeToShow / 60;
				int sec = timeToShow - min * 60;
				_time.setText(" " + String.format("%02d", min) + ":" + String.format("%02d", sec));
				_score.setText("" + scoreToShow);
			}

			@Override
			public void onFinish() {
				_time.setText(" " + String.format("%02d", 0) + ":" + String.format("%02d", 0));
				_score.setText("" + achievedScore);
			}
		});

	}

}
