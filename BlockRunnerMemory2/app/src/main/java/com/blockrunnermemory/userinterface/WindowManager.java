package com.blockrunnermemory.userinterface;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.blockrunnermemory.R;
import com.blockrunnermemory.states.PlayState;
import com.blockrunnermemory.states.State;


public class WindowManager {
	
	public static void showPopupSettings() {
		RelativeLayout popupContainer = (RelativeLayout) State.fragmentActivity.findViewById(R.id.winup_container);
		popupContainer.removeAllViews();
		ImageView imageView = new ImageView(State.context);
		imageView.setBackgroundColor(Color.parseColor("#88555555"));
		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		imageView.setClickable(true);
		popupContainer.addView(imageView);
		SettingsView settingsView = new SettingsView(State.context);
		int width = State.context.getResources().getDimensionPixelSize(R.dimen.popup_settings_width);
		int height = State.context.getResources().getDimensionPixelSize(R.dimen.popup_settings_height);
		LayoutParams params = new LayoutParams(width, height);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		popupContainer.addView(settingsView, params);
		ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(settingsView, "scaleX", 0f, 1f);
		ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(settingsView, "scaleY", 0f, 1f);
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
		animatorSet.setDuration(500);
		animatorSet.setInterpolator(new DecelerateInterpolator(2));
		animatorSet.start();
	}

	public static void showPopupWon(PlayState playState) {
		RelativeLayout popupContainer = (RelativeLayout) State.fragmentActivity.findViewById(R.id.winup_container);
		popupContainer.removeAllViews();
		WinView winView = new WinView(State.context);
		winView.setGameState(playState);
		int width = State.context.getResources().getDimensionPixelSize(R.dimen.popup_won_width);
		int height = State.context.getResources().getDimensionPixelSize(R.dimen.popup_won_height);
		LayoutParams params = new LayoutParams(width, height);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		popupContainer.addView(winView, params);
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(winView, "scaleX", 0f, 1f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(winView, "scaleY", 0f, 1f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(scaleX, scaleY);
		animatorSet.setDuration(500);
		animatorSet.setInterpolator(new DecelerateInterpolator(2));
		winView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animatorSet.start();
	}

	public static void closeWinup() {
		final RelativeLayout winupContainer = (RelativeLayout) State.fragmentActivity.findViewById(R.id.winup_container);
		int childCount = winupContainer.getChildCount();
		if (childCount > 0) {
			View bgs = null;
			View winUp = null;
			if (childCount == 1) {
				winUp = winupContainer.getChildAt(0);
			} else {
				bgs = winupContainer.getChildAt(0);
				winUp = winupContainer.getChildAt(1);
			}

			AnimatorSet animatorSet = new AnimatorSet();
			ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(winUp, "scaleX", 0f);
			ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(winUp, "scaleY", 0f);
			if (childCount > 1) {
				ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(bgs, "alpha", 0f);
				animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
			} else {
				animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
			}
			animatorSet.setDuration(300);
			animatorSet.setInterpolator(new AccelerateInterpolator(2));
			animatorSet.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					winupContainer.removeAllViews();
				}
			});
			animatorSet.start();
		}
	}

	public static boolean isShown() {
		RelativeLayout popupContainer = (RelativeLayout) State.fragmentActivity.findViewById(R.id.winup_container);
		return popupContainer.getChildCount() > 0;
	}
}
