package com.RandNprograming.tapemeasurecalculator.calculator;

import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;
import com.RandNprograming.tapemeasurecalculator.calculator.screens.LoadingScreen;
import com.RandNprograming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;

public class StartCalculator extends AndroidTapemeasureCalculator {

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}