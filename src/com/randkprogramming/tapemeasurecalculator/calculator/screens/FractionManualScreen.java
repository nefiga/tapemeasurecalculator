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

            checkUpperSection(event);
            checkLowerSection(event);
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
        g.drawString(fraction, x_position, 200, paint);
    }

    public static void setupLayout() {

        setupLayoutUpper();
    }

    //--------------------------------
    // Button Layout - Upper section
    //--------------------------------
    private static final int NUM_ROWS_UPPER = 3;
    private static final int NUM_COLS_UPPER = 4;
    private static final int BUTTON_WIDTH_UPPER = 115;
    private static final int BUTTON_HEIGHT_UPPER = 115;
    private static final int HORIZONTAL_GAP_UPPER = 68;
    private static final int VERTICAL_GAP_UPPER = 30;
    private static final int X_OFFSET_UPPER = 0;
    private static final int Y_OFFSET_UPPER = 300;

    // Half the gap on one side plus half the gap on the other to allow for bigger touch areas.
    private static final int TOUCH_WIDTH_UPPER = BUTTON_WIDTH_UPPER + HORIZONTAL_GAP_UPPER;
    private static final int TOUCH_HEIGHT_UPPER = BUTTON_HEIGHT_UPPER + VERTICAL_GAP_UPPER;

    private static final Button buttonLayoutUpper[][] = new Button[][]{
            new Button[]{Button.ManualFractions.SEVEN, Button.ManualFractions.EIGHT, Button.ManualFractions.NINE, Button.ManualFractions.BACK},
            new Button[]{Button.ManualFractions.FOUR, Button.ManualFractions.FIVE, Button.ManualFractions.SIX},
            new Button[]{Button.ManualFractions.ONE, Button.ManualFractions.TWO, Button.ManualFractions.THREE, Button.ManualFractions.CLEAR}
    };

    private static final int[] xCoordsUpper = new int[NUM_COLS_UPPER];
    private static final int[] yCoordsUpper = new int[NUM_ROWS_UPPER];

    /** Calculates the top left x and y coordinates for each button. This method makes adjustments so that
     * touch events pressed within gap areas will go to the closest button. */
    private static void setupLayoutUpper() {

        for (int col = 0; col < NUM_COLS_UPPER; col++) {
            xCoordsUpper[col] = (X_OFFSET_UPPER + HORIZONTAL_GAP_UPPER + col *
                    (BUTTON_WIDTH_UPPER + HORIZONTAL_GAP_UPPER)) - (HORIZONTAL_GAP_UPPER / 2);
        }
        for (int row = 0; row < NUM_ROWS_UPPER; row++) {
            yCoordsUpper[row] = (Y_OFFSET_UPPER + VERTICAL_GAP_UPPER + row *
                    (BUTTON_HEIGHT_UPPER + VERTICAL_GAP_UPPER)) - (VERTICAL_GAP_UPPER / 2);
        }
    }

    private void checkUpperSection(Input.TouchEvent event) {

        for (int row = 0; row < NUM_ROWS_UPPER; row++) {
            for (int col = 0; col < NUM_COLS_UPPER; col++) {

                if (touchIsInBounds(event, xCoordsUpper[col], yCoordsUpper[row], TOUCH_WIDTH_UPPER, TOUCH_HEIGHT_UPPER)) {

                    // No button here
                    if(row == 1 && col == 3)
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




    //--------------------------------
    // Button Layout - Lower section
    //--------------------------------
    private static final int[] denom_x = {34,282,519};
    private static final int[] denom_y = {770,925};
    private static final Button denom_buttons[][] = new Button[][]{

    };

    private void checkLowerSection(Input.TouchEvent event) {

        for(int row = 0; row < 2; row++) {
            for(int col = 0; col < 3; col++) {

                if(touchIsInBounds(event,denom_x[col],denom_y[row],237,155)) {
                    switch (event.type) {
                        case Input.TouchEvent.TOUCH_DOWN:    manager.setButtonPressed(denom_buttons[row][col]); break;
                        case Input.TouchEvent.TOUCH_UP:      manager.setButtonReleased(denom_buttons[row][col]); break;
                        case Input.TouchEvent.TOUCH_DRAGGED: break;
                    }
                    return;
                }
            }
        }

    }




    private void checkEnterButton(Input.TouchEvent event) {

        // Enter Button
        if(touchIsInBounds(event,34,1095,711,155)) {
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
