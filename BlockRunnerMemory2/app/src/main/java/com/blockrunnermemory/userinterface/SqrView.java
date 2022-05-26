package com.blockrunnermemory.userinterface;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blockrunnermemory.R;
import com.blockrunnermemory.event.Flip;


public class SqrView extends FrameLayout {

	private RelativeLayout relativeLayout;
	private ImageView imageView;
	private boolean isFlipped = true;

	public SqrView(Context context) {
		this(context, null);
	}

	public SqrView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public static SqrView fromXml(Context context, ViewGroup parent) {
		return (SqrView) LayoutInflater.from(context).inflate(R.layout.tile_view, parent, false);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		relativeLayout = (RelativeLayout) findViewById(R.id.image_top);
		imageView = (ImageView) findViewById(R.id.image);
	}

	public void setTileImage(Bitmap bitmap) {
		imageView.setImageBitmap(bitmap);
	}

	public void flipUp() {
		isFlipped = false;
		flip();
	}

	public void flipDown() {
		isFlipped = true;
		flip();
	}
	
	private void flip() {
		Flip flipAnimation = new Flip(relativeLayout, imageView);
		if (relativeLayout.getVisibility() == View.GONE) {
			flipAnimation.RP();
		}
		startAnimation(flipAnimation);
	}

	public boolean isFlippedDown() {
		return isFlipped;
	}

}
