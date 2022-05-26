package com.blockrunnermemory.support;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.ThumbnailUtils;

import com.blockrunnermemory.states.State;

public class Util {

	public static int px(int dp) {
		return (int) (State.context.getResources().getDisplayMetrics().density * dp);
	}

	public static int wWidth() {
		return State.context.getResources().getDisplayMetrics().widthPixels;
	}

	public static int wHeight() {
		return State.context.getResources().getDisplayMetrics().heightPixels;
	}

	public static Bitmap crop(Bitmap source, int newHeight, int newWidth) {
		int sourceWidth = source.getWidth();
		int sourceHeight = source.getHeight();
		float xScale = (float) newWidth / sourceWidth;
		float yScale = (float) newHeight / sourceHeight;
		float scale = Math.max(xScale, yScale);
		float scaledWidth = scale * sourceWidth;
		float scaledHeight = scale * sourceHeight;
		float left = (newWidth - scaledWidth) / 2;
		float top = (newHeight - scaledHeight) / 2;
		RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
		Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
		Canvas canvas = new Canvas(dest);
		canvas.drawBitmap(source, null, targetRect, null);

		return dest;
	}

	public static Bitmap scaleDown(int resource, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(State.context.getResources(), resource);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(State.context.getResources(), resource, options);
	}

	public static Bitmap downscaleBitmap(Bitmap wallpaper, int factor) {
		int widthPixels = wallpaper.getWidth() / factor;
		int heightPixels = wallpaper.getHeight() / factor;
		return ThumbnailUtils.extractThumbnail(wallpaper, widthPixels, heightPixels);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
}
