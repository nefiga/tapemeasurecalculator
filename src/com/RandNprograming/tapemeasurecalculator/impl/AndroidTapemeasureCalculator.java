package com.RandNprograming.tapemeasurecalculator.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
//import com.google.ads.AdSize;
//import com.google.ads.AdView;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Input;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;

public abstract class AndroidTapemeasureCalculator extends Activity implements Calculator {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Input input;
    Screen screen;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int frameBufferWidth = 800;
        int frameBufferHeight = 1280;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);

        float scaleX = (float)frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float)frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        AndroidSound.loadSounds(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();
        setContentView(renderView);

        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
    }

    @Override
    public void onBackPressed() {
        screen.androidBackButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        renderView.pause();
        screen.pause();

        if (isFinishing()) screen.dispose();
    }

    public Input getInput() {return input;}

    public Graphics getGraphics() {return graphics;}

    public void setScreen(Screen screen) {
        if (screen == null) throw new IllegalArgumentException("Screen must not be null");
        this.screen.pause();
        this.screen.dispose();
        this.screen = screen;
        screen.resume();
        screen.update(0);
    }

    public Screen getCurrentScreen() {return screen;}
}
