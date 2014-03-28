package com.RandNprograming.tapemeasurecalculator.impl;

import android.view.View.OnTouchListener;
import com.RandNprograming.tapemeasurecalculator.interfaces.Input.TouchEvent;

import java.util.List;

public interface TouchHandler extends OnTouchListener {

    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
