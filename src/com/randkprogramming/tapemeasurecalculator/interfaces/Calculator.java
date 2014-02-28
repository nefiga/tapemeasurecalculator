package com.randkprogramming.tapemeasurecalculator.interfaces;

public interface Calculator {

    public Input getInput();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
}
