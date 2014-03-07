package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import android.graphics.Paint;
import android.graphics.Typeface;
import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.Button;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.randkprogramming.tapemeasurecalculator.impl.AndroidFastRenderView;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;

import java.util.List;

public class FractionManualScreen extends Screen{

    private static final Typeface font = Typeface.create("DEFAULT_BOLD", Typeface.BOLD);
    Paint paint = new Paint();

    CalculatorInputManager manager;
    private static String fraction = "";

    public FractionManualScreen(Calculator calculator) {
        super(calculator);
        manager = new CalculatorInputManager();
        paint.setTypeface(font);
        paint.setTextSize(100);
    }

    public static void clear() {
        fraction = "";
    }

    public static void backspace() {

        if(fraction.length() > 0) {
            fraction = fraction.substring(0, fraction.length()-1);
        }
    }

    public static void addForwardSlash() {
        if (fraction.length() > 0 && !fraction.contains("/")) {
            fraction += "/";
        }
    }

    public static void addNumber(String number) {
        if (fraction.length() < 2 && ! fraction.contains("/")) {
            addNumerator(number);
        }
        else if (fraction.length() >= 1 && !fraction.contains("/") && fraction.codePointCount(fraction.indexOf("/"), fraction.length()) < 2) {
            addDenominator(number);
        }
    }

    public static void addNumerator(String n) {
            fraction += n;
    }

    public static void addDenominator(String d) {
            fraction += d;
    }

    public static void enter() {
        CalcState.addFraction(fraction);
        clear();
        Calculator c = AndroidFastRenderView.getCalculator();
        c.setScreen(new MainCalculatorScreen(c));
    }

    @Override public void update(float deltaTime) {

        List<Input.TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (Input.TouchEvent event : touchEvents) {

            checkMainSection(event);
            checkEnterButton(event);
            manager.updateHoldTime(deltaTime);
        }
    }

    @Override public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.clear(0xffffff);
        g.drawPixmap(Assets.manual_fraction_screen, 0, 0);
        float offset = paint.measureText(fraction) / 2;
        float x_position = 400 - offset;
        g.drawString(fraction, x_position, 220, paint);
    }

    //--------------------------------
    // Button Layout - Upper section
    //--------------------------------
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 4;
    private static final int BUTTON_WIDTH = 115;
    private static final int BUTTON_HEIGHT = 115;
    private static final int HORIZONTAL_GAP = 68;
    private static final int VERTICAL_GAP = 30;
    private static final int X_OFFSET = 0;
    private static final int Y_OFFSET = 465;

    // Half the gap on one side plus half the gap on the other to allow for bigger touch areas.
    private static final int TOUCH_WIDTH = BUTTON_WIDTH + HORIZONTAL_GAP;
    private static final int TOUCH_HEIGHT = BUTTON_HEIGHT + VERTICAL_GAP;

    private static final Button buttonLayoutUpper[][] = new Button[][]{
            new Button[]{Button.ManualFractions.CLEAR, null, null, Button.ManualFractions.BACK},
            new Button[]{Button.ManualFractions.SEVEN, Button.ManualFractions.EIGHT, Button.ManualFractions.NINE, Button.ManualFractions.FRACTION},
            new Button[]{Button.ManualFractions.FOUR, Button.ManualFractions.FIVE, Button.ManualFractions.SIX, Button.ManualFractions.FRACTION},
            new Button[]{Button.ManualFractions.ONE, Button.ManualFractions.TWO, Button.ManualFractions.THREE, Button.ManualFractions.FRACTION},
            new Button[]{null,Button.ManualFractions.ZERO,null,null}
    };

    private static final int[] xCoordsUpper = new int[NUM_COLS];
    private static final int[] yCoordsUpper = new int[NUM_ROWS];

    /** Calculates the top left x and y coordinates for each button. This method makes adjustments so that
     * touch events pressed within gap areas will go to the closest button. */
    public static void setupLayout() {

        for (int col = 0; col < NUM_COLS; col++) {
            xCoordsUpper[col] = (X_OFFSET + HORIZONTAL_GAP + col *
                    (BUTTON_WIDTH + HORIZONTAL_GAP)) - (HORIZONTAL_GAP / 2);
        }
        for (int row = 0; row < NUM_ROWS; row++) {
            yCoordsUpper[row] = (Y_OFFSET + VERTICAL_GAP + row *
                    (BUTTON_HEIGHT + VERTICAL_GAP)) - (VERTICAL_GAP / 2);
        }
    }

    private void checkMainSection(Input.TouchEvent event) {

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                if (touchIsInBounds(event, xCoordsUpper[col], yCoordsUpper[row], TOUCH_WIDTH, TOUCH_HEIGHT)) {

                    // No buttons in these positions: (0,1),(0,2),(4,0),(4,2),(4,3)
                    if((row == 0 && (col == 1 || col == 2)) || (row == 3 && col != 1))
                        return;

                    switch (event.type) {
                        case Input.TouchEvent.TOUCH_DOWN:    manager.setButtonPressed(buttonLayoutUpper[row][col]); break;
                        case Input.TouchEvent.TOUCH_UP:      manager.setButtonReleased(buttonLayoutUpper[row][col]); break;
                        case Input.TouchEvent.TOUCH_DRAGGED: break;
                    }
                    return;
                }
            }
        }
    }


    private void checkEnterButton(Input.TouchEvent event) {

        // Enter Button
        if(touchIsInBounds(event,34,1070,731,170)) {
            switch (event.type) {
                case Input.TouchEvent.TOUCH_DOWN:    manager.setButtonPressed(Button.ManualFractions.ENTER); break;
                case Input.TouchEvent.TOUCH_UP:      manager.setButtonReleased(Button.ManualFractions.ENTER); break;
                case Input.TouchEvent.TOUCH_DRAGGED: break;
            }
        }
    }

    // Checks to see if your finger is within an area
    private boolean touchIsInBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        return (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}

    @Override public void androidBackButton() {
        clear();
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
