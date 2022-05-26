package com.blockrunnermemory.event;


import com.blockrunnermemory.connectevent.GameConnection;
import com.blockrunnermemory.connectevent.GameObject;

public class Start extends GameConnection {

	public static final String NAME = Start.class.getName();

	@Override
	protected void launch(GameObject gameObject) {		gameObject.onEvent(this);	}

	@Override
	public String getName() {		return NAME;	}

}
