package com.randkprogramming.tapemeasurecalculator;

public interface Calculator {

    public Input getInput();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
}
