package com.randkprogramming.tapemeasurecalculator;

import com.randkprogramming.tapemeasurecalculator.Graphics.PixmapFormat;

public interface Pixmap {

    public int getWidth();
    public int getHeight();
    public PixmapFormat getFormat();
    public void dispose();
}
