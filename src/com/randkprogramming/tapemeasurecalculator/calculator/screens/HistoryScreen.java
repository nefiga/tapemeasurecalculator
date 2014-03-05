package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import android.graphics.Paint;
import android.graphics.Typeface;
import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.*;
import com.randkprogramming.tapemeasurecalculator.impl.AndroidFastRenderView;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;

import java.util.List;

public class HistoryScreen extends Screen {

    CalculatorInputManager manager;
    int[] yCoordinatesText = { 255, 365, 475, 585, 695, 815, 925, 1035, 1145, 1255 };

    Paint paint = new Paint();
    private static final Typeface historyFont = Typeface.create("DEFAULT_BOLD", Typeface.BOLD);

    public HistoryScreen(Calculator calculator) {
        super(calculator);
        paint.setTypeface(historyFont);
        paint.setTextSize(50);
    }

    @Override public void update(float deltaTime) {

        List<Input.TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (Input.TouchEvent event : touchEvents) {

            checkEquationBounds(event);
        }
    }
    @Override public void present(float deltaTime) {

        Graphics g = calculator.getGraphics();
        g.drawPixmap(Assets.history, 0, 0);

        int i = 0;
        for(Equation equation : CalcHistory.getHistory()) {

            if( i < yCoordinatesText.length)
                g.drawString(equation.getString(), 10, yCoordinatesText[i], paint);
            i++;
        }

    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidBackButton() {

        calculator.setScreen(new MainCalculatorScreen(calculator));
    }

    private static final int NUM_ROWS = 10;
    private static final int X_BEGIN = 0;
    private static final int Y_BEGIN = 190;
    private static final int ROW_WIDTH = 800;
    private static final int ROW_HEIGHT = 30;
    private static final int GAP_BETWEEN_ROWS = 20;

    public void checkEquationBounds(Input.TouchEvent event) {

        int x = X_BEGIN;
        int y = Y_BEGIN;

        for(int i = 0; i < NUM_ROWS; i++) {

            if(touchIsInBounds(event, x, y, x+ROW_WIDTH, y+ROW_HEIGHT)) {
                selectEquation(i);
                return;
            }
            y = y + ROW_HEIGHT + GAP_BETWEEN_ROWS;
        }

    }

    public void selectEquation(int index) {

        Equation e = CalcHistory.getHistoryAt(index);
        if(e != null) {
            Calculator c = AndroidFastRenderView.getCalculator();
            c.setScreen(new MainCalculatorScreen(c));
            CalcState.equation = e.copy();
            CalcState.equation.updateEquation();
        }
    }

    // Checks to see if your finger is within an area
    public boolean touchIsInBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        return (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1);
    }

}
