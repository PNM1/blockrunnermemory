package com.blockrunnermemory.event;

import com.blockrunnermemory.connectevent.GameConnection;
import com.blockrunnermemory.connectevent.GameObject;
import com.blockrunnermemory.themeselect.ThemeState;

public class SelectTheme extends GameConnection {

	public static final String NAME = SelectTheme.class.getName();

	@Override
	protected void launch(GameObject gameObject) {		gameObject.onEvent(this);	}

	@Override
	public String getName() {		return NAME;	}

	public final ThemeState themeState;

	public SelectTheme(ThemeState themeState) {		this.themeState = themeState;	}

}
