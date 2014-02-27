package com.randkprogramming.tapemeasurecalculator.calculator;

import android.graphics.Paint;
import android.graphics.Typeface;
import com.randkprogramming.tapemeasurecalculator.Calculator;
import com.randkprogramming.tapemeasurecalculator.Graphics;
import com.randkprogramming.tapemeasurecalculator.Input.TouchEvent;
import com.randkprogramming.tapemeasurecalculator.Screen;
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

    //--------------------------------
    // Upper Section Button Layout
    //--------------------------------
    private static final int NUM_ROWS_UPPER = 4;
    private static final int NUM_COLS_UPPER = 5;
    private static final int BUTTON_WIDTH_UPPER = 140;
    private static final int BUTTON_HEIGHT_UPPER = 190;
    private static final int xUpper[] = {10, 170, 330, 490, 650};
    private static final int yUpper[] = {225, 440, 650, 865};
    private static final Button layoutUpper[][] = new Button[][]{
            new Button[]{Button.Number.SEVEN, Button.Number.EIGHT, Button.Number.NINE, Button.Calculate.BACKSPACE, Button.Calculate.CLEAR},
            new Button[]{Button.Number.FOUR, Button.Number.FIVE, Button.Number.SIX, Button.Operator.PLUS, Button.Operator.TIMES},
            new Button[]{Button.Number.ONE, Button.Number.TWO, Button.Number.THREE, Button.Operator.MINUS, Button.Operator.DIVIDE},
            new Button[]{Button.Calculate.DECIMAL_POINT, Button.Number.ZERO, Button.Calculate.EQUALS, Button.Calculate.FEET, Button.Calculate.INCHES}
    };

    //-------------------------------
    // Lower Section Button Layout
    //-------------------------------
    private static final int NUM_ROWS_LOWER = 1;
    private static final int NUM_COLS_LOWER = 4;
    private static final int BUTTON_WIDTH_LOWER = 180;
    private static final int BUTTON_HEIGHT_LOWER = 190;
    private static final int xLower[] = {10, 210, 410, 610};
    private static final int yLower[] = {1075};
    private static final Button layoutLower[][] = new Button[][]{
            new Button[] { Button.Special.FRACTION, Button.Special.PRECISION, Button.Special.DISPLAY, Button.Special.INFO }
    };

    //--------------------------
    // Constructor
    //--------------------------
    public MainCalculatorScreen(Calculator calculator) {
        super(calculator);
        paint.setTypeface(answerFont);
        paint.setTextSize(50);
    }

    @Override
    public void update(float deltaTime) {

        debugTimer += deltaTime;
        if (debugTimer >= 2.0f) {
            debugTimer = 0;

            System.out.println(CalculateEquation.buildString());
        }

//      List<Input.KeyEvent> keyEvents = calculator.getInput().getKeyEvents();

        // Check for TouchEvents
        List<TouchEvent> touchEvents = calculator.getInput().getTouchEvent();

        for (TouchEvent event : touchEvents) {

            if (event == null)
                continue;

            upperSection(event);
            lowerSection(event);

            manager.updateHoldTime(deltaTime);
        }
    }

    /**
     * Lays out the upper section of the app.
     * @param event The touch event
     */
    private void upperSection(TouchEvent event) {

        for (int row = 0; row < NUM_ROWS_UPPER; row++) {
            for (int col = 0; col < NUM_COLS_UPPER; col++) {

                if (touchIsInBounds(event, xUpper[col], yUpper[row], BUTTON_WIDTH_UPPER, BUTTON_HEIGHT_UPPER)) {
                    switch (event.type) {
                        case TouchEvent.TOUCH_DOWN:    manager.setButtonPressed(layoutUpper[row][col]);  break;
                        case TouchEvent.TOUCH_UP:      manager.setButtonReleased(layoutUpper[row][col]); break;
                        case TouchEvent.TOUCH_DRAGGED: break;
                    }
                    return; // Since no buttons overlap, we return
                }
            }
        }
    }

    /**
     * Lays out the bottom section of the app.
     * @param event The touch event
     */
    private void lowerSection(TouchEvent event) {

        for (int row = 0; row < NUM_ROWS_LOWER; row++) {
            for (int col = 0; col < NUM_COLS_LOWER; col++) {

                if (touchIsInBounds(event, xLower[col], yLower[row], BUTTON_WIDTH_LOWER, BUTTON_HEIGHT_LOWER)) {
                    switch (event.type) {
                        case TouchEvent.TOUCH_DOWN:    manager.setButtonPressed(layoutLower[row][col]);  break;
                        case TouchEvent.TOUCH_UP:      manager.setButtonReleased(layoutLower[row][col]); break;
                        case TouchEvent.TOUCH_DRAGGED: break;
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {

        //Draw Images
        Graphics g = calculator.getGraphics();

        g.drawPixmap(Assets.main_calculator, 0, 0);
        g.drawString(CalculateEquation.buildString(), 10, 50, paint);

        g.drawPixmap(Assets.precision[CalculatorState.precisionMode.ordinal()], 210, 1075);
        g.drawPixmap(Assets.displayIn[CalculatorState.displayMode.ordinal()], 410, 1075);
    }

    // Checks to see if your finger is within an area
    public boolean touchIsInBounds(TouchEvent event, int x, int y, int width, int height) {
        return (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidBackButton() {}
}
