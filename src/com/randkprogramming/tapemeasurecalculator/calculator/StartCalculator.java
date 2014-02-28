package com.randkprogramming.tapemeasurecalculator.calculator;

import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.screens.LoadingScreen;
import com.randkprogramming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;

public class StartCalculator extends AndroidTapemeasureCalculator {

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}