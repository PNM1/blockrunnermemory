package com.blockrunnermemory.event;


import com.blockrunnermemory.connectevent.GameConnection;
import com.blockrunnermemory.connectevent.GameObject;

public class ToBgs extends GameConnection {

	public static final String NAME = ToBgs.class.getName();

	@Override
	protected void launch(GameObject gameObject) {		gameObject.onEvent(this);	}

	@Override
	public String getName() {		return NAME;	}

}
