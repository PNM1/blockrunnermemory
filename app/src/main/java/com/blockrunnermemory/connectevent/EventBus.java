package com.blockrunnermemory.connectevent;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

	private Handler handler;
	private static EventBus eventBus = null;
	private final Map<String, List<GameObject>>
			timing = Collections.synchronizedMap(new HashMap<String,
			List<GameObject>>());
	private Object object = new Object();

	private EventBus() {		handler = new Handler();	}

	public static EventBus getStatus() {
		if (eventBus == null) {
			eventBus = new EventBus();
		}
		return eventBus;
	}

	synchronized public void play(String Name, GameObject gameObject) {
		List<GameObject> observers = timing.get(Name);
		if (observers == null) {
			observers = Collections.synchronizedList(new ArrayList<GameObject>());
		}
		observers.add(gameObject);
		timing.put(Name, observers);
	}

	synchronized public void dontplay(String Name, GameObject gameObject) {
		List<GameObject> observers = timing.get(Name);
		if (observers != null) {
			observers.remove(gameObject);
		}
	}

	public void call(Game game) {
		synchronized (object) {
			List<GameObject> observers = timing.get(game.getName());
			if (observers != null) {
				for (GameObject observer : observers) {
					GameConnection abstractEvent = (GameConnection) game;
					abstractEvent.launch(observer);
				}
			}
		}
	}
	
	public void call(final Game game, long delay) {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() { EventBus.this.call(game);			}
		}, delay);
	}

}
