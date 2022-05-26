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

public interface GameObject {

	void onEvent(FlipCard flipCard);

	void onEvent(SelectDifficult selectDifficult);

	void onEvent(Hide hide);

	void onEvent(FlipDown flipDown);

	void onEvent(Start start);

	void onEvent(SelectTheme selectTheme);

	void onEvent(WinEvent winEvent);

	void onEvent(BackPoly backPoly);

	void onEvent(NextPoly nextPoly);

	void onEvent(ToBgs toBgs);

}
