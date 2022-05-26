package com.blockrunnermemory.event;

import com.blockrunnermemory.connectevent.GameConnection;
import com.blockrunnermemory.connectevent.GameObject;

public class FlipCard extends GameConnection {

	public static final String NAME = FlipCard.class.getName();
	@Override
	protected void launch(GameObject gameObject) {
		gameObject.onEvent(this);
	}

	@Override
	public String getName() {		return NAME;	}

	public final int x;
	
	public FlipCard(int x) {		this.x = x;	}
	

}
