package com.blockrunnermemory.event;

import com.blockrunnermemory.connectevent.GameConnection;
import com.blockrunnermemory.connectevent.GameObject;

public class FlipDown extends GameConnection {

	public static final String NAME = FlipDown.class.getName();
	@Override

	protected void launch(GameObject gameObject) {
		gameObject.onEvent(this);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public FlipDown() {	}
}
