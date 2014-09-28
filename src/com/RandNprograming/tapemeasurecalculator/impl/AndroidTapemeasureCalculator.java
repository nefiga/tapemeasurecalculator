package com.RandNprograming.tapemeasurecalculator.impl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.*;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Input;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public abstract class AndroidTapemeasureCalculator extends Activity implements Calculator {
    AndroidFastRenderView renderView;
    WindowManager.LayoutParams windowParams;
    WindowManager wm;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Graphics graphics;
    Input input;
    Screen screen;
    AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = this.getSharedPreferences("com.RandNprograming.tapemeasurecalculator", Context.MODE_PRIVATE);
        editor = preferences.edit();
        if (preferences.contains("order_of_ops")) {
            CalcState.orderOfOps = preferences.getBoolean("order_of_ops", CalcState.orderOfOps);
        }
        if (preferences.contains("display_tape_image")) {
            CalcState.displayTapeImage = preferences.getBoolean("display_tape_image", CalcState.displayTapeImage);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int frameBufferWidth = 800;
        int frameBufferHeight = 1380;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);

        float scaleX = (float) frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        AndroidSound.loadSounds(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();
        setContentView(renderView);

        // window manager preparation
        windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.BOTTOM;
        windowParams.x = 0;
        windowParams.y = 0;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = 0;

        wm = getWindowManager();
// Create the adView
        adView = new AdView(this, AdSize.BANNER, "ca-app-pub-5915229248659770/8234904641");

// Initiate a generic request to load it with an ad
        adView.loadAd(new AdRequest());

// Add adView to WindowManager
        wm.addView(adView, windowParams);
    }

    @Override
    public void onBackPressed() {
        screen.androidBackButton(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        screen.androidOptionButton();
        return false;
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
        editor.putBoolean("order_of_ops", CalcState.orderOfOps);
        editor.putBoolean("display_tape_image", CalcState.displayTapeImage);
        editor.commit();
        renderView.pause();
        screen.pause();

        if (isFinishing()) screen.dispose();
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.removeAllViews();
            adView.destroy();
        }
        super.onDestroy();
    }

    public Input getInput() {
        return input;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public void setScreen(Screen screen) {
        if (screen == null) throw new IllegalArgumentException("Screen must not be null");
        this.screen.pause();
        this.screen.dispose();
        this.screen = screen;
        screen.resume();
        screen.update(0);
    }

    public Screen getCurrentScreen() {
        return screen;
    }
}
