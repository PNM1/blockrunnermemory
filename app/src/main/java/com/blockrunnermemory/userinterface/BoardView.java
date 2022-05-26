package com.blockrunnermemory.userinterface;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;


import com.blockrunnermemory.R;
import com.blockrunnermemory.event.FlipCard;
import com.blockrunnermemory.states.Board;
import com.blockrunnermemory.states.Cfg;
import com.blockrunnermemory.states.Play;
import com.blockrunnermemory.states.State;
import com.blockrunnermemory.support.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardView extends LinearLayout {

	private LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 
			LayoutParams.WRAP_CONTENT);
	private LayoutParams title;
	private int wWidth;
	private int wHeight;
	private Cfg cfg;
	private Board board;
	private Map<Integer, SqrView> integerTileViewMap;
	private List<Integer> flippedUp = new ArrayList<Integer>();
	private boolean isLocked = false;
	private int _size;

	public BoardView(Context context) {
		this(context, null);
	}

	public BoardView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER);
		int otst = getResources().getDimensionPixelSize(R.dimen.otstup);
		int dop = getResources().getDimensionPixelSize(R.dimen.board_dop);
		wHeight = getResources().getDisplayMetrics().heightPixels - otst - dop*2;
		wWidth = getResources().getDisplayMetrics().widthPixels - dop*2 - Util.px(20);
		integerTileViewMap = new HashMap<Integer, SqrView>();
		setClipToPadding(false);
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	public static BoardView fromXml(Context context, ViewGroup parent) {
		return (BoardView) LayoutInflater.from(context).inflate(R.layout.board_view, parent, false);
	}
	public void setBoard(Play play) {
		cfg = play.cfg;
		board = play.board;
		int sost = getResources().getDimensionPixelSize(R.dimen.card_margin);
		float density = getResources().getDisplayMetrics().density;
		sost = Math.max((int) (1 * density), (int) (sost - cfg.difficult * 2 * density));
		int sumMargin = 0;
		for (int row = 0; row < cfg.numRows; row++) {
			sumMargin += sost * 2;
		}
		int tilesHeight = (wHeight - sumMargin) / cfg.numRows;
		int tilesWidth = (wWidth - sumMargin) / cfg.numInRow;
		_size = Math.min(tilesHeight, tilesWidth);
		title = new LayoutParams(_size, _size);
		title.setMargins(sost, sost, sost, sost);
		build();
	}
	private void build() {
		for (int row = 0; row < cfg.numRows; row++) {
			addBoardRow(row);
		}
		setClipChildren(false);
	}

	private void addBoardRow(int rowNum) {

		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setGravity(Gravity.CENTER);

		for (int tile = 0; tile < cfg.numInRow; tile++) {
			addTile(rowNum * cfg.numInRow + tile, linearLayout);
		}
		addView(linearLayout, layoutParams);
		linearLayout.setClipChildren(false);
	}

	private void addTile(final int id, ViewGroup parent) {
		final SqrView sqrView = SqrView.fromXml(getContext(), parent);
		sqrView.setLayoutParams(title);
		parent.addView(sqrView);
		parent.setClipChildren(false);
		integerTileViewMap.put(id, sqrView);

		new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... params) {
				return board.getTileBitmap(id, _size);
			}
			
			@Override
			protected void onPostExecute(Bitmap result) {
				sqrView.setTileImage(result);
			}
		}.execute();
		sqrView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLocked && sqrView.isFlippedDown()) {
					sqrView.flipUp();
					flippedUp.add(id);
					if (flippedUp.size() == 2) {
						isLocked = true;
					}
					State.eventBus.call(new FlipCard(id));
				}
			}
		});

		ObjectAnimator scaleX = ObjectAnimator.ofFloat(sqrView, "scaleX", 0.8f, 1f);
		scaleX.setInterpolator(new BounceInterpolator());
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(sqrView, "scaleY", 0.8f, 1f);
		scaleY.setInterpolator(new BounceInterpolator());
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(scaleX, scaleY);
		animatorSet.setDuration(500);
		sqrView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animatorSet.start();
	}

	public void flipDownAll() {
		for (Integer id : flippedUp) {
			integerTileViewMap.get(id).flipDown();
		}
		flippedUp.clear();
		isLocked = false;
	}

	public void hideCards(int id1, int id2) {
		animateHide(integerTileViewMap.get(id1));
		animateHide(integerTileViewMap.get(id2));
		flippedUp.clear();
		isLocked = false;
	}

	protected void animateHide(final SqrView v) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 0f);
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				v.setLayerType(View.LAYER_TYPE_NONE, null);
				v.setVisibility(View.INVISIBLE);
			}
		});
		animator.setDuration(100);
		v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animator.start();
	}

}
