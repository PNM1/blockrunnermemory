package com.blockrunnermemory.connectevent;


import com.blockrunnermemory.event.BackPoly;
import com.blockrunnermemory.event.SelectDifficult;
import com.blockrunnermemory.event.FlipCard;
import com.blockrunnermemory.event.FlipDown;
import com.blockrunnermemory.event.WinEvent;
import com.blockrunnermemory.event.Hide;
import com.blockrunnermemory.event.NextPoly;
import com.blockrunnermemory.event.ToBgs;
import com.blockrunnermemory.event.Start;
import com.blockrunnermemory.event.SelectTheme;

public class GameObjectAdapter implements GameObject {

	public void onEvent(FlipCard flipCard) { throw new UnsupportedOperationException();	}
	@Override
	public void onEvent(SelectDifficult selectDifficult) { throw new UnsupportedOperationException();	}
	@Override
	public void onEvent(Hide hide) {		throw new UnsupportedOperationException();	}
	@Override
	public void onEvent(FlipDown flipDown) { throw new UnsupportedOperationException();	}
	@Override
	public void onEvent(Start start) {		throw new UnsupportedOperationException();	}
	@Override
	public void onEvent(SelectTheme selectTheme) {		throw new UnsupportedOperationException();	}
	@Override
	public void onEvent(WinEvent winEvent) {		throw new UnsupportedOperationException();	}
	@Override
	public void onEvent(BackPoly backPoly) {		throw new UnsupportedOperationException();		}
	@Override
	public void onEvent(NextPoly nextPoly) {		throw new UnsupportedOperationException();		}
	@Override
	public void onEvent(ToBgs toBgs) {		throw new UnsupportedOperationException();		}

}
