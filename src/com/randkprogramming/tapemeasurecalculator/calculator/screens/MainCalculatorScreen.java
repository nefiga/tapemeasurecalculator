package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import android.graphics.Paint;
import android.graphics.Typeface;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input.TouchEvent;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.*;

import java.util.List;

public class MainCalculatorScreen extends Screen {

    //--------------------------
    // Fields
    //--------------------------
    private CalculatorInputManager manager = new CalculatorInputManager();

    private Paint paint = new Paint();
    private static final Typeface answerFont = Typeface.create("DEFAULT_BOLD", Typeface.BOLD);

    private float debugTimer = 0;

    //--------------------------
    // Constructor
    //--------------------------
    public MainCalculatorScreen(Calculator calculator) {
        super(calculator);
        paint.setTypeface(answerFont);
        paint.setTextSize(75);
    }

    public void printDebugStatements(float deltaTime) {

        debugTimer += deltaTime;
        if (debugTimer >= 5.0f) {
            debugTimer = 0;

            System.out.println(CalcHistory.print());
        }
    }

    @Override
    public void update(float deltaTime) {

//        printDebugStatements(deltaTime);

        List<TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (TouchEvent event : touchEvents) {

            checkTouchEvent(event);
            manager.updateHoldTime(deltaTime);
        }
    }

    @Override
    public void present(float deltaTime) {

        // Draw Images
        Graphics g = calculator.getGraphics();

        g.drawPixmap(Assets.main_calculator, 0, 0);
        g.drawString(CalcState.equation.getString(), 40, 100, paint);

        g.drawPixmap(Assets.fractionOrDecimal[CalcState.fractionOrDecimal.ordinal()], 68, 1055);
        if(CalcState.fractionOrDecimal == DisplayModes.FractionOrDecimal.FRACTION_OPTION) {
            g.drawPixmap(Assets.fractionPrecision[CalcState.fractionPrecision.ordinal()], 251, 1055);
        }
        g.drawPixmap(Assets.units[CalcState.displayUnits.ordinal()], 434, 1055);
    }

    // Checks to see if your finger is within an area
    public boolean touchIsInBounds(TouchEvent event, int x, int y, int width, int height) {
        return (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1);
    }


    //--------------------------------
    // Button Layout
    //--------------------------------
    private static final int NUM_ROWS = 6;
    private static final int NUM_COLS = 4;

    private static final int BUTTON_WIDTH = 115;
    private static final int BUTTON_HEIGHT = 115;

    private static final int HORIZONTAL_GAP = 68;
    private static final int VERTICAL_GAP = 30;

    private static final int X_OFFSET = 0;
    private static final int Y_OFFSET = 300;

    private static final Button buttonLayout[][] = new Button[][]{
            new Button[]{Button.Calculate.CLEAR, Button.Operator.DIVIDE, Button.Operator.TIMES, Button.Calculate.BACKSPACE},
            new Button[]{Button.Number.SEVEN, Button.Number.EIGHT, Button.Number.NINE, Button.Operator.MINUS},
            new Button[]{Button.Number.FOUR, Button.Number.FIVE, Button.Number.SIX, Button.Operator.PLUS},
            new Button[]{Button.Number.ONE, Button.Number.TWO, Button.Number.THREE, Button.Calculate.FEET},
            new Button[]{Button.Calculate.DECIMAL_POINT, Button.Number.ZERO, Button.Calculate.FRACTION, Button.Calculate.EQUALS},
            new Button[]{Button.Special.FRACTION_OR_DECIMAL, Button.Special.FRACTION_PRECISION, Button.Special.DISPLAY_UNITS, Button.Special.INFO},
    };

    private static final int[] xCoords = new int[NUM_COLS];
    private static final int[] yCoords = new int[NUM_ROWS];

    /** Calculates the top left x and y coordinates for each button. */
    public static void setupLayout() {

        for (int col = 0; col < NUM_COLS; col++) {
            xCoords[col] = X_OFFSET + HORIZONTAL_GAP + col * (BUTTON_WIDTH + HORIZONTAL_GAP);
        }
        for (int row = 0; row < NUM_ROWS; row++) {
            yCoords[row] = Y_OFFSET + VERTICAL_GAP + row * (BUTTON_HEIGHT + VERTICAL_GAP);
        }
    }

    /** Checks to see if the event fired is in bounds of a button. If it is, then it lets
     * the manager know which button was pressed or released.
     * @param event The event being fired. */
    private void checkTouchEvent(TouchEvent event) {

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                if (touchIsInBounds(event, xCoords[col], yCoords[row], BUTTON_WIDTH, BUTTON_HEIGHT)) {
                    switch (event.type) {
                        case TouchEvent.TOUCH_DOWN:    manager.setButtonPressed(buttonLayout[row][col]);  break;
                        case TouchEvent.TOUCH_UP:      manager.setButtonReleased(buttonLayout[row][col]); break;
                        case TouchEvent.TOUCH_DRAGGED: break;
                    }
                    return; // Since no buttons overlap, we return
                }
            }
        }
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidBackButton() {}
}
