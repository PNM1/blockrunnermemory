package com.blockrunnermemory;





import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blockrunnermemory.ViewConrol.DifficultySelect;
import com.blockrunnermemory.ViewConrol.GameProcess;
import com.blockrunnermemory.ViewConrol.MenuFragment;
import com.blockrunnermemory.ViewConrol.ThemeSelect;
import com.blockrunnermemory.event.ToBgs;
import com.blockrunnermemory.states.State;


import java.util.ArrayList;
import java.util.List;

public class ScreenGameManager {

	private static ScreenGameManager screenGameManager = null;
	private static List<Screen> screens = new ArrayList<Screen>();
	private FragmentManager fragmentManager;

	private ScreenGameManager() {}

	public static enum Screen {
		STASRTMENU,
		PLAY,
		SELECTTHEME,
		SELECTDIFFICULT

	}
	private Fragment getFragment(Screen screen) {
		switch (screen) {
			case STASRTMENU:
				return new MenuFragment();
			case PLAY:
				return new GameProcess();
			case SELECTTHEME:
				return new ThemeSelect();
			case SELECTDIFFICULT:
				return new DifficultySelect();
			default:
				break;
		}		return null;
	}

	public static ScreenGameManager getStatus() {
		if (screenGameManager == null) {
			screenGameManager = new ScreenGameManager();
		}
		return screenGameManager;
	}
	public void openScreen(Screen screen) {

		fragmentManager = State.fragmentActivity.getSupportFragmentManager();
		
		if (screen == Screen.PLAY && screens.get(screens.size() - 1) == Screen.PLAY) {
			screens.remove(screens.size() - 1);
		} else if (screen == Screen.SELECTDIFFICULT && screens.get(screens.size() - 1) == Screen.PLAY) {
			screens.remove(screens.size() - 1);
			screens.remove(screens.size() - 1);
		}
		Fragment fragment = getFragment(screen);
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, fragment);
		fragmentTransaction.commit();
		screens.add(screen);
	}

	public boolean onBack() {
		if (screens.size() > 0) {
			Screen thatremove = screens.get(screens.size() - 1);
			screens.remove(screens.size() - 1);
			if (screens.size() == 0) {	return true;	}
			Screen screen = screens.get(screens.size() - 1);
			screens.remove(screens.size() - 1);
			openScreen(screen);
			if ((screen == Screen.SELECTTHEME || screen == Screen.STASRTMENU) &&
					(thatremove == Screen.SELECTDIFFICULT || thatremove == Screen.PLAY)) {
				State.eventBus.call(new ToBgs());
			}			return false;
		}		return true;
	}


	public static Screen getLastScreen() {
		return screens.get(screens.size() - 1);
	}
}
