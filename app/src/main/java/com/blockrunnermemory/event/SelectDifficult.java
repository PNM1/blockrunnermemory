package com.blockrunnermemory.event;

import com.blockrunnermemory.connectevent.GameConnection;
import com.blockrunnermemory.connectevent.GameObject;


public class SelectDifficult extends GameConnection {

	public static final String NAME = SelectDifficult.class.getName();
	
	@Override
	protected void launch(GameObject gameObject) {		gameObject.onEvent(this);	}

	@Override
	public String getName() {		return NAME;	}

	public final int difficult;

	public SelectDifficult(int difficult) {		this.difficult = difficult;	}

}
