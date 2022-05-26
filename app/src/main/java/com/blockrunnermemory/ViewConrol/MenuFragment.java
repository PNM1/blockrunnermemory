package com.blockrunnermemory.ViewConrol;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.blockrunnermemory.DBHelper;
import com.blockrunnermemory.R;
import com.blockrunnermemory.event.Start;
import com.blockrunnermemory.states.State;
import com.blockrunnermemory.support.Music;
import com.blockrunnermemory.support.Util;
import com.blockrunnermemory.userinterface.WindowManager;

public class MenuFragment extends Fragment {

	private ImageView _title;
	private ImageView _start;
	private ImageView _lights;
	private ImageView _setting;
	private ImageView _info;
	DBHelper dbHelper;

	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle statusState) {
		View view = layoutInflater.inflate(R.layout.menu_fragment, container, false);
		_title = (ImageView) view.findViewById(R.id.title);
		_start = (ImageView) view.findViewById(R.id.start_game_button);
		_setting = (ImageView) view.findViewById(R.id.settings_game_button);
		_setting.setSoundEffectsEnabled(false);
		_setting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				WindowManager.showPopupSettings();
			}
		});
		_info = (ImageView) view.findViewById(R.id.info);
		_info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Автор: Повх Н.М.", Toast.LENGTH_LONG).show();
			}
		});
		_lights = (ImageView) view.findViewById(R.id.start_game_button_lights);
		_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				animateAllAssetsOff(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						State.eventBus.call(new Start());
					}
				});
			}
		});
		startLightsAnimation();
		startTitleAnimation();

		// форновая музыка
		Music.playBgs();
		return view;
	}

	protected void animateAllAssetsOff(AnimatorListenerAdapter adapter) {
		// Название
		ObjectAnimator title = ObjectAnimator.ofFloat(_title, "translationY", Util.px(-200));
		title.setInterpolator(new AccelerateInterpolator(2));
		title.setDuration(300);

	// Эффекты кнопки
		ObjectAnimator lightsX = ObjectAnimator.ofFloat(_lights, "scaleX", 0f);
		ObjectAnimator lightsY = ObjectAnimator.ofFloat(_lights, "scaleY", 0f);
		//настройки
		ObjectAnimator settings = ObjectAnimator.ofFloat(_setting, "translationY", Util.px(120));
		settings.setInterpolator(new AccelerateInterpolator(2));
		settings.setDuration(300);
		//инфо
		ObjectAnimator info = ObjectAnimator.ofFloat(_info, "translationY", Util.px(120));
		info.setInterpolator(new AccelerateInterpolator(2));
		info.setDuration(300);

		// старт
		ObjectAnimator start = ObjectAnimator.ofFloat(_start, "translationY", Util.px(130));
		start.setInterpolator(new AccelerateInterpolator(2));
		start.setDuration(300);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(title, lightsX, lightsY, settings, info, start);
		animatorSet.addListener(adapter);
		animatorSet.start();
	}

	private void startTitleAnimation() {
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(_title, "scaleY", 1.2f);
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(_title, "scaleX", 1.03f);
		scaleY.setDuration(200);
		scaleX.setDuration(200);
		ObjectAnimator scaleYBack = ObjectAnimator.ofFloat(_title, "scaleY", 1f);
		ObjectAnimator scaleXBack = ObjectAnimator.ofFloat(_title, "scaleX", 1f);
		scaleYBack.setDuration(500);
		scaleXBack.setDuration(500);
		scaleYBack.setInterpolator(new BounceInterpolator());
		scaleXBack.setInterpolator(new BounceInterpolator());
		final AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setStartDelay(650);
		animatorSet.playSequentially(scaleY, scaleX, scaleYBack, scaleXBack);
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				animatorSet.setStartDelay(750);
				animatorSet.start();
			}
		});
		_title.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animatorSet.start();
	}
	private void startLightsAnimation() {
		ObjectAnimator animator = ObjectAnimator.ofFloat(_lights, "rotation", 0f, 360f);
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		animator.setDuration(6000);
		animator.setRepeatCount(ValueAnimator.INFINITE);
		_lights.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animator.start();
	}

}
