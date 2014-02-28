package com.randkprogramming.tapemeasurecalculator.interfaces;

import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics.PixmapFormat;

public interface Pixmap {

    public int getWidth();
    public int getHeight();
    public PixmapFormat getFormat();
    public void dispose();
}
