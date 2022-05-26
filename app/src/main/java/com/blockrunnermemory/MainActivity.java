package com.blockrunnermemory;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.blockrunnermemory.connectevent.EventBus;
import com.blockrunnermemory.event.BackPoly;
import com.blockrunnermemory.states.State;
import com.blockrunnermemory.support.Cache;
import com.blockrunnermemory.support.Util;
import com.blockrunnermemory.userinterface.WindowManager;


public class MainActivity extends FragmentActivity {
	private ImageView bgs;
	DBHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		State.context = getApplicationContext();
		State.mainThread = MainThread.getStatus();
		State.eventBus = EventBus.getStatus();

		setContentView(R.layout.activity_main);
		bgs = (ImageView) findViewById(R.id.background_image);

		State.fragmentActivity = this;
		State.mainThread.start();
		State.mainThread.setBackgroundImageView(bgs);

		setBackgroundImage();

		ScreenGameManager.getStatus().openScreen(ScreenGameManager.Screen.STASRTMENU);

		dbHelper = new com.blockrunnermemory.DBHelper(this);
	}

	@Override
	protected void onDestroy() {
		SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.query(com.blockrunnermemory.DBHelper.TABLE_NAME,
				null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			int idIndex = cursor.getColumnIndex(com.blockrunnermemory.DBHelper.KEY_ID);
			int timeIndex = cursor.getColumnIndex(com.blockrunnermemory.DBHelper.KEY_TIME);
			int scoreIndex = cursor.getColumnIndex(com.blockrunnermemory.DBHelper.KEY_SCORE);
			do{
				Log.d("mLog", "ID = "+cursor.getInt(idIndex) +
						", time = "+ cursor.getString(timeIndex) +
						", score = " + cursor.getString(scoreIndex));
				Cache.bestTime = cursor.getString(timeIndex);
				Cache.bestStar = cursor.getString(scoreIndex);
			} while (cursor.moveToNext());
		}else {
			Log.d("mLog", "0 строк");
		} cursor.close();


		sqLiteDatabase.delete(com.blockrunnermemory.DBHelper.TABLE_NAME, null, null);
		sqLiteDatabase.close();
		State.mainThread.stop();
		super.onDestroy();

	}

	@Override
	public void onBackPressed() {
		SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(com.blockrunnermemory.DBHelper.KEY_TIME, Cache.bestTime);
		contentValues.put(com.blockrunnermemory.DBHelper.KEY_SCORE, Cache.bestStar);
		sqLiteDatabase.insert(com.blockrunnermemory.DBHelper.TABLE_NAME,
				null, contentValues);
		if (WindowManager.isShown()) {
			WindowManager.closeWinup();
			if (ScreenGameManager.getLastScreen() == ScreenGameManager.Screen.PLAY) {
				State.eventBus.call(new BackPoly());
			}
		} else if (ScreenGameManager.getStatus().onBack()) {
			super.onBackPressed();
		}
	}

	private void setBackgroundImage() {

		Bitmap bitmap = Util.scaleDown(R.drawable.background, Util.wWidth()*2, Util.wHeight()*2);
		bitmap = Util.crop(bitmap, Util.wHeight(), Util.wWidth());
		bitmap = Util.downscaleBitmap(bitmap, 2);
		bgs.setImageBitmap(bitmap);
	}

}
