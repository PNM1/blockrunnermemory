package com.blockrunnermemory.ViewConrol;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;


import androidx.fragment.app.Fragment;

import com.blockrunnermemory.R;
import com.blockrunnermemory.event.SelectTheme;
import com.blockrunnermemory.states.State;
import com.blockrunnermemory.support.Cache;
import com.blockrunnermemory.themeselect.ThemeState;
import com.blockrunnermemory.themeselect.Themes;

import java.util.Locale;

public class ThemeSelect extends Fragment {

	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle statusState) {
		View view = LayoutInflater.from(State.context).inflate(R.layout.theme_select_fragment, container, false);
		View animals = view.findViewById(R.id.theme_animals_container);
		View monsters = view.findViewById(R.id.theme_monsters_container);

		final ThemeState themeStateAnimals = Themes.createAnimal();
		setStars((ImageView) animals.findViewById(R.id.theme_animals), themeStateAnimals, "animals");
		final ThemeState themeStateMonsters = Themes.createMoster();
		setStars((ImageView) monsters.findViewById(R.id.theme_monsters), themeStateMonsters, "monsters");

		animals.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				State.eventBus.call(new SelectTheme(themeStateAnimals));
			}
		});

		monsters.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				State.eventBus.call(new SelectTheme(themeStateMonsters));
			}
		});
		animateShow(animals);
		animateShow(monsters);

		return view;
	}

	private void animateShow(View view) {
		ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
		ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(300);
		animatorSet.playTogether(animatorScaleX, animatorScaleY);
		animatorSet.setInterpolator(new DecelerateInterpolator(2));
		view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animatorSet.start();
	}

	private void setStars(ImageView imageView, ThemeState themeState, String type) {
		int sum = 0;
		for (int difficulty = 1; difficulty <= 6; difficulty++) {
			sum += Cache.getBestCountStars(themeState.dx, difficulty);
		}
		int num = sum / 6;
		if (num != 0) {
			String drawableResourceName = String.format(Locale.US, type + "_theme_star_%d", num);
			int drawableResourceId = State.context.getResources().getIdentifier(drawableResourceName, "drawable", State.context.getPackageName());
			imageView.setImageResource(drawableResourceId);
		}
	}
}
