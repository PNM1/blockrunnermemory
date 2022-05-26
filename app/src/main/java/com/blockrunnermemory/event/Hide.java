package com.blockrunnermemory.event;

import com.blockrunnermemory.connectevent.GameConnection;
import com.blockrunnermemory.connectevent.GameObject;

public class Hide extends GameConnection {

	public static final String NAME = Hide.class.getName();


	@Override
	protected void launch(GameObject gameObject) {		gameObject.onEvent(this);	}

	@Override
	public String getName() {		return NAME;	}
	public int x;
	public int y;

	public Hide(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
