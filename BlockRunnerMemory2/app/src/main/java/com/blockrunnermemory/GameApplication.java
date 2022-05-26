package com.blockrunnermemory;

import android.app.Application;

import com.blockrunnermemory.support.FontLoader;


public class GameApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		FontLoader.loadFonts(this);

	}
}
