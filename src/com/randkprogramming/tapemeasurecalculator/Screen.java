package com.randkprogramming.tapemeasurecalculator;

public abstract class Screen {

    protected final Calculator calculator;

    public Screen(Calculator calculator) {
        this.calculator = calculator;
    }

    public abstract void update(float deltaTime);
    public abstract void present(float deltaTime);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();
    public abstract void androidBackButton();
}