package com.randkprogramming.tapemeasurecalculator.calculator;

import com.randkprogramming.tapemeasurecalculator.Screen;
import com.randkprogramming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;

public class StartCalculator extends AndroidTapemeasureCalculator {

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
