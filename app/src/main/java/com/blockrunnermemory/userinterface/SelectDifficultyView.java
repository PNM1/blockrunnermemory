package com.blockrunnermemory.userinterface;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.blockrunnermemory.R;
import com.blockrunnermemory.states.State;

import java.util.Locale;

public class SelectDifficultyView extends LinearLayout {

	private ImageView imageView;
	
	public SelectDifficultyView(Context context) {
		this(context, null);
	}
	
	public SelectDifficultyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.difficult_view, this, true);
		setOrientation(LinearLayout.VERTICAL);
		imageView = (ImageView) findViewById(R.id.title);
	}
	
	public void setDifficulty(int difficulty, int stars) {
		String titleResource = String.format(Locale.US, "button_difficulty_%d_star_%d", difficulty, stars);
		int drawableResourceId = State.context.getResources().getIdentifier(titleResource, "drawable", State.context.getPackageName());
		imageView.setImageResource(drawableResourceId);
	}
	
}
