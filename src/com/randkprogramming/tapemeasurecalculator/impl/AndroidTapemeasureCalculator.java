package com.randkprogramming.tapemeasurecalculator.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;

import java.util.List;

public abstract class AndroidTapemeasureCalculator extends Activity implements Calculator {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Input input;
    Screen screen;
    WakeLock wakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int frameBufferWidth = 800;
        int frameBufferHeight = 1280;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);

        float scaleX = (float)frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float)frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();
        setContentView(renderView);

        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(powerManager.FULL_WAKE_LOCK, "GLCalculator");
    }

    @Override
    public void onBackPressed() {
        screen.androidBackButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing()) screen.dispose();
    }

    public Input getInput() {return input;}

    public Graphics getGraphics() {return graphics;}

    public void setScreen(Screen screen) {
        if (screen == null) throw new IllegalArgumentException("Screen must not be null");
        input.getTouchEvent();
        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() {return screen;}
}
