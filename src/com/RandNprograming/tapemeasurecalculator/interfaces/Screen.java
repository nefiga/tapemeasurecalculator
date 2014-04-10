package com.RandNprograming.tapemeasurecalculator.interfaces;

import android.app.Activity;
import com.RandNprograming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;

public abstract class Screen {

    protected final Calculator calculator;

    // Checks to see if your finger is within an area
    public static boolean touchIsInBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        return (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1);
    }

    public Screen(Calculator calculator) {
        this.calculator = calculator;
    }

    public abstract void update(float deltaTime);
    public abstract void present(float deltaTime);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();
    public abstract void androidBackButton(AndroidTapemeasureCalculator activity);
    public abstract void androidOptionButton();
}