package com.blockrunnermemory.ViewConrol;

import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.blockrunnermemory.R;
import com.blockrunnermemory.event.SelectDifficult;
import com.blockrunnermemory.states.State;
import com.blockrunnermemory.support.Cache;
import com.blockrunnermemory.themeselect.ThemeState;
import com.blockrunnermemory.userinterface.SelectDifficultyView;

public class DifficultySelect extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle statusState) {
        View view = LayoutInflater.from(State.context).inflate(R.layout.difficulty_select_fragment, container, false);
        ThemeState themeState = State.mainThread.getSelectedTheme();

        SelectDifficultyView d1 = (SelectDifficultyView) view.findViewById(R.id.select_difficulty_1);
        d1.setDifficulty(1, Cache.getBestCountStars(themeState.dx, 1));
        setOnClick(d1, 1);

        SelectDifficultyView d2 = (SelectDifficultyView) view.findViewById(R.id.select_difficulty_2);
        d2.setDifficulty(2, Cache.getBestCountStars(themeState.dx, 2));
        setOnClick(d2, 2);

        SelectDifficultyView d3 = (SelectDifficultyView) view.findViewById(R.id.select_difficulty_3);
        d3.setDifficulty(3, Cache.getBestCountStars(themeState.dx, 3));
        setOnClick(d3, 3);

        SelectDifficultyView d4 = (SelectDifficultyView) view.findViewById(R.id.select_difficulty_4);
        d4.setDifficulty(4, Cache.getBestCountStars(themeState.dx, 4));
        setOnClick(d4, 4);

        SelectDifficultyView d5 = (SelectDifficultyView) view.findViewById(R.id.select_difficulty_5);
        d5.setDifficulty(5, Cache.getBestCountStars(themeState.dx, 5));
        setOnClick(d5, 5);

        SelectDifficultyView d6 = (SelectDifficultyView) view.findViewById(R.id.select_difficulty_6);
        d6.setDifficulty(6, Cache.getBestCountStars(themeState.dx, 6));
        setOnClick(d6, 6);

        animate(d1, d2, d3, d4, d5, d6);

        Typeface type = Typeface.createFromAsset(State.context.getAssets(), "fonts/ObelixPro.ttf");


        TextView text1 = (TextView) view.findViewById(R.id.time_difficulty_1);
        text1.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        text1.setTypeface(type);
        text1.setText(getBestTimeForStage(themeState.dx, 1));

        TextView text2 = (TextView) view.findViewById(R.id.time_difficulty_2);
        text2.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        text2.setTypeface(type);
        text2.setText(getBestTimeForStage(themeState.dx, 2));

        TextView text3 = (TextView) view.findViewById(R.id.time_difficulty_3);
        text3.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        text3.setTypeface(type);
        text3.setText(getBestTimeForStage(themeState.dx, 3));

        TextView text4 = (TextView) view.findViewById(R.id.time_difficulty_4);
        text4.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        text4.setTypeface(type);
        text4.setText(getBestTimeForStage(themeState.dx, 4));

        TextView text5 = (TextView) view.findViewById(R.id.time_difficulty_5);
        text5.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        text5.setTypeface(type);
        text5.setText(getBestTimeForStage(themeState.dx, 5));

        TextView text6 = (TextView) view.findViewById(R.id.time_difficulty_6);
        text6.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        text6.setTypeface(type);
        text6.setText(getBestTimeForStage(themeState.dx, 6));

        return view;

    }

    private String getBestTimeForStage(int theme, int difficulty) {
        int bestTime = Cache.getBestTime(theme, difficulty);
        if (bestTime != -1) {
            int minutes = (bestTime % 3600) / 60;
            int seconds = (bestTime) % 60;
            String result = String.format("Лучшее: %02d:%02d", minutes, seconds);
            return result;
        } else {
            String result = "Лучшее: -";
            return result;
        }
    }

    private void animate(View... view) {
        AnimatorSet animatorSet = new AnimatorSet();
        Builder builder = animatorSet.play(new AnimatorSet());
        for (int i = 0; i < view.length; i++) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view[i], "scaleX", 0.8f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view[i], "scaleY", 0.8f, 1f);
            builder.with(scaleX).with(scaleY);
        }
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.start();
    }

    private void setOnClick(View view, final int difficulty) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State.eventBus.call(new SelectDifficult(difficulty));
            }
        });
    }


}
