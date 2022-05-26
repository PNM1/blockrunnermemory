package com.blockrunnermemory.event;


import com.blockrunnermemory.connectevent.GameConnection;
import com.blockrunnermemory.connectevent.GameObject;
import com.blockrunnermemory.states.PlayState;

public class WinEvent extends GameConnection {

	public static final String NAME = WinEvent.class.getName();

	@Override
	protected void launch(GameObject gameObject) {		gameObject.onEvent(this);	}

	@Override
	public String getName() {		return NAME;	}

	public PlayState playState;

	public WinEvent(PlayState playState) {

		this.playState = playState;
	}


}
