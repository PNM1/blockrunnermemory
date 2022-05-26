package com.blockrunnermemory.support;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.widget.TextView;

public class FontLoader {

	public static final int ObelixPro = 0;

	public static enum Font {
		ObelixPro(FontLoader.ObelixPro, "fonts/ObelixPro.ttf");
		private int value;
		private String path;
		private Font(int value, String path) {
			this.value = value;
			this.path = path;
		}

		public static String getByValue(int val) {
			for (Font font : values()) {
				if (font.value == val) {
					return font.path;
				}
			}
			return null;
		}
	}

	private static SparseArray<Typeface> font = new SparseArray<Typeface>();
	private static boolean isLoad = false;

	public static void loadFonts(Context context) {
		for (int i = 0; i < Font.values().length; i++) {
			font.put(i, Typeface.createFromAsset(context.getAssets(), Font.getByValue(i)));
		}
		isLoad = true;
	}
	public static void setTypeface(Context context, TextView[] textViews, Font font) {
		setTypeFaceToTextViews(context, textViews, font, Typeface.NORMAL);
	}
	private static void setTypeFaceToTextViews(Context context, TextView[] textViews, Font font, int fontStyle) {
		if (!isLoad) {
			loadFonts(context);
		}
		Typeface currentFont = FontLoader.font.get(font.value);
		for (int i = 0; i < textViews.length; i++) {
			if (textViews[i] != null)
				textViews[i].setTypeface(currentFont, fontStyle);
		}
	}

}
