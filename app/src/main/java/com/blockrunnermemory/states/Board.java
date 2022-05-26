package com.blockrunnermemory.states;

import android.graphics.Bitmap;

import com.blockrunnermemory.support.Util;
import com.blockrunnermemory.themeselect.Themes;

import java.util.Map;


public class Board {

	public Map<Integer, Integer> pairs;
	public Map<Integer, String> urla;
	// {0-mosters_20}
	public Bitmap getTileBitmap(int i, int size) {
		String string = urla.get(i);
		if (string.contains(Themes.URI)) {
			String drawableResourceName = string.substring(Themes.URI.length());
			int resId = State.context.getResources().getIdentifier(drawableResourceName,
					"drawable", State.context.getPackageName());
			Bitmap bitmap = Util.scaleDown(resId, size, size);
			return Util.crop(bitmap, size, size);
		}
		return null;
	}

	public boolean isPair(int x, int y) {
		Integer integer = pairs.get(x);
		if (integer == null) {
			return false;
		}
		return integer.equals(y);
	}

}
