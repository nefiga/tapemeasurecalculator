package com.randkprogramming.tapemeasurecalculator.impl;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class AndroidSound {

    private static SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);

    private static int click_id;

    public static void loadSounds(Context c) {
//        click_id = soundPool.load(c, android.R.raw.click, 1);
    }

    public static void playClick(int id) {
        soundPool.play(click_id, 1, 1, 0, 0, 1);
    }

}
