package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;

public class FractionManualScreen extends Screen{

    CalculatorInputManager manager;
    private static String fraction;

    public FractionManualScreen(Calculator calculator) {
        super(calculator);
        manager = new CalculatorInputManager();
    }

    public static void clear() {
        fraction = "";
    }

    public static void backspace() {
        fraction = fraction.substring(0, fraction.length()-1);
    }

    public static void addNumerator(String n) {
        if (fraction.length() < 2) {
            fraction += n;
        }
    }

    public static void addDenominator(String d) {
        if (fraction.length() >= 1) {
            fraction += d;
            CalcState.addFraction(fraction);
            clear();
        }
    }


    @Override public void update(float deltaTime) {}

    @Override public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.clear(0xffffff);
        g.drawPixmap(Assets.manual_fraction_screen, 0, 0);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}

    @Override public void androidBackButton() {
        clear();
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
