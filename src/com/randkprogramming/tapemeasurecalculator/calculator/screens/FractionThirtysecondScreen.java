package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.Button;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;

import java.util.List;

public class FractionThirtysecondScreen extends Screen{

    CalculatorInputManager manager;

    public FractionThirtysecondScreen(Calculator calculator, CalculatorInputManager manager) {
        super(calculator);
        this.manager = manager;
        setupLayout();
    }

    //--------------------------------
    // Button Layout
    //--------------------------------
    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 4;

    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 120;

    private static final int HORIZONTAL_GAP = 40;
    private static final int VERTICAL_GAP = 20;

    private static final Button buttonLayout[][] = new Button[][]{
            new Button[] {Button.ThirtySeconds.ONE, Button.Sixteenths.ONE, Button.ThirtySeconds.THREE, Button.OtherFractions.ONE_EIGHTH},
            new Button[] {Button.ThirtySeconds.FIVE, Button.Sixteenths.THREE, Button.ThirtySeconds.SEVEN, Button.OtherFractions.ONE_QUARTER},
            new Button[] {Button.ThirtySeconds.NINE, Button.Sixteenths.FIVE, Button.ThirtySeconds.ELEVEN, Button.OtherFractions.THREE_EIGHTHS},
            new Button[] {Button.ThirtySeconds.THIRTEEN, Button.Sixteenths.SEVEN, Button.ThirtySeconds.FIFTEEN, Button.OtherFractions.ONE_HALF},
            new Button[] {Button.ThirtySeconds.SEVENTEEN, Button.Sixteenths.NINE, Button.ThirtySeconds.NINETEEN, Button.OtherFractions.FIVE_EIGHTHS},
            new Button[] {Button.ThirtySeconds.TWENTY_ONE, Button.Sixteenths.ELEVEN, Button.ThirtySeconds.TWENTY_THREE, Button.OtherFractions.THREE_QUARTERS},
            new Button[] {Button.ThirtySeconds.TWENTY_FIVE, Button.Sixteenths.THIRTEEN, Button.ThirtySeconds.TWENTY_SEVEN, Button.OtherFractions.SEVEN_EIGHTHS},
            new Button[] {Button.ThirtySeconds.TWENTY_NINE, Button.Sixteenths.FIFTEEN, Button.ThirtySeconds.THIRTY_ONE}
    };

    private static final int[] xCoords = new int[NUM_COLS];
    private static final int[] yCoords = new int[NUM_ROWS];

    // Half the gap on one side plus half the gap on the other to allow for bigger touch areas.
    private static final int TOUCH_WIDTH = BUTTON_WIDTH + HORIZONTAL_GAP;
    private static final int TOUCH_HEIGHT = BUTTON_HEIGHT + VERTICAL_GAP;

    public static void setupLayout() {

        for (int col = 0; col < NUM_COLS; col++) {
            xCoords[col] = (HORIZONTAL_GAP + col * (BUTTON_WIDTH + HORIZONTAL_GAP)) - (HORIZONTAL_GAP / 2);
        }
        for (int row = 0; row < NUM_ROWS; row++) {
            yCoords[row] = (10 + VERTICAL_GAP + row * (BUTTON_HEIGHT + VERTICAL_GAP)) - (VERTICAL_GAP / 2);
        }
    }

    private void checkTouchEvent(Input.TouchEvent event) {

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                if (touchIsInBounds(event, xCoords[col], yCoords[row], TOUCH_WIDTH, TOUCH_HEIGHT)) {
                    switch (event.type) {
                        case Input.TouchEvent.TOUCH_DOWN:    manager.setButtonPressed(buttonLayout[row][col]);  break;
                        case Input.TouchEvent.TOUCH_UP:      manager.setButtonReleased(buttonLayout[row][col]); break;
                        case Input.TouchEvent.TOUCH_DRAGGED: break;
                    }
                    return; // Since no buttons overlap, we return
                }
            }
        }
    }

    public boolean touchIsInBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        return (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1);
    }

    @Override public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (Input.TouchEvent event : touchEvents) {

            checkTouchEvent(event);
            manager.updateHoldTime(deltaTime);
        }
    }

    @Override public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.drawPixmap(Assets.thirtyseconds_screen, 0, 0);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidBackButton() {
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
