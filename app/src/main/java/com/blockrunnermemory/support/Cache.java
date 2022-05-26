package com.blockrunnermemory.support;

import android.content.Context;
import android.content.SharedPreferences;

import com.blockrunnermemory.states.State;

public class Cache {

    private static final String NAME = "com.blockrunnermemory";
    public static String BestCountStarsKey = "theme_%d_difficulty_%d";
    public static String bestTimeKey = "themetime_%d_difficultytime_%d";

    public static void saveStar(int theme, int difficulty, int stars) {
        int top = getBestCountStars(theme, difficulty);
        if (stars > top) {
            SharedPreferences sharedPreferences = State.context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            String key = String.format(BestCountStarsKey, theme, difficulty);
            edit.putInt(key, stars).commit();
        }    }
    public static void saveTime(int theme, int difficulty, int passed) {
        int best = getBestTime(theme, difficulty);
        if (passed < best || best == -1) {
            SharedPreferences sharedPreferences = State.context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String timeKey = String.format(bestTimeKey, theme, difficulty);
            editor.putInt(timeKey, passed);
            editor.commit();
        }    }

    public static int getBestCountStars(int theme, int difficulty) {
        SharedPreferences sharedPreferences = State.context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        String key = String.format(BestCountStarsKey, theme, difficulty);
        return sharedPreferences.getInt(key, 0);
    }
    public static int getBestTime(int theme, int difficulty) {

        SharedPreferences sharedPreferences = State.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String key = String.format(bestTimeKey, theme, difficulty);
        return sharedPreferences.getInt(key, -1);
    }

    public static String bestStar = "0";
    public static String bestTime = "0";

    public static String getBDStar(int theme, int difficulty, int stars) {
        int top = getBestCountStars(theme, difficulty);
        if (stars > top) {
            bestStar = Integer.toString(top);
        } return bestStar;
    }
    public static String getBDTime(int theme, int difficulty, int passed) {
        int best = getBestTime(theme, difficulty);
        if (passed < best || best == -1) {
           bestTime = Integer.toString(best);
        } return bestTime;
    }

}
