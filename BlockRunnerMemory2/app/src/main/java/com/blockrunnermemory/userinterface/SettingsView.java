package com.blockrunnermemory.userinterface;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockrunnermemory.R;
import com.blockrunnermemory.states.State;
import com.blockrunnermemory.support.FontLoader;
import com.blockrunnermemory.support.Music;

public class SettingsView extends LinearLayout {

	private ImageView imageView;
	private TextView textView;

	public SettingsView(Context context) {
		this(context, null);
	}

	public SettingsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundResource(R.drawable.settings_winup);

		LayoutInflater.from(getContext()).inflate(R.layout.winup_settings_view, this, true);
		textView = (TextView) findViewById(R.id.sound_off_text);
		TextView rateView = (TextView) findViewById(R.id.rate_text);

		FontLoader.setTypeface(context, new TextView[] { textView, rateView }, FontLoader.Font.ObelixPro);
		imageView = (ImageView) findViewById(R.id.sound_image);
		View soundOff = findViewById(R.id.sound_off);
		soundOff.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Music.off = !Music.off;
				setMusicButton();
			}
		});
		View yt = findViewById(R.id.yt);
		yt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					State.fragmentActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCKO9bvd2YckuTXV5pdzzvgw")));
				} catch (android.content.ActivityNotFoundException anfe) {
					State.fragmentActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCKO9bvd2YckuTXV5pdzzvgw")));
				}
			}
		});
		setMusicButton();
	}

	private void setMusicButton() {
		if (Music.off) {
			textView.setText("Sound OFF");
			imageView.setImageResource(R.drawable.button_music_off);
		} else {
			textView.setText("Sound ON");
			imageView.setImageResource(R.drawable.button_music_on);
		}
	}
}
