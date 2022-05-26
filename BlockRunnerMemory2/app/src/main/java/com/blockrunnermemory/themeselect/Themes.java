package com.blockrunnermemory.themeselect;

import android.graphics.Bitmap;

import com.blockrunnermemory.states.State;
import com.blockrunnermemory.support.Util;

import java.util.ArrayList;

public class Themes {

	public static String URI = "drawable://";

	public static ThemeState createAnimal() {
		ThemeState themeState = new ThemeState();
		themeState.dx = 1;
		themeState.name = "Animals";
		themeState.bgsImage = URI + "back_animals";
		themeState.tileImage = new ArrayList<String>();
		for (int i = 1; i < 35; i++) {
			themeState.tileImage.add(URI + String.format("animals_%d", i));
		}
		return themeState;
	}
	public static ThemeState createMoster() {
		ThemeState themeState = new ThemeState();
		themeState.dx = 2;
		themeState.name = "Mosters";
		themeState.bgsImage = URI + "back_horror";
		themeState.tileImage = new ArrayList<String>();
		for (int i = 1; i < 41; i++) {
			themeState.tileImage.add(URI + String.format("mosters_%d", i));
		}
		return themeState;
	}
	public static Bitmap getBackgroundImage(ThemeState themeState) {
		String resName = themeState.bgsImage.substring(Themes.URI.length());
		int drawableResourceId = State.context.getResources().getIdentifier(resName,
				"drawable", State.context.getPackageName());
		Bitmap bitmap = Util.scaleDown(drawableResourceId, Util.wWidth(), Util.wHeight());
		return bitmap;
	}

}
