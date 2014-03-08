/*

package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;
import com.randkprogramming.tapemeasurecalculator.calculator.buttons.Button;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;

import java.util.List;

public class FractionSixteenthScreen extends Screen {

    CalculatorInputManager manager;

    public FractionSixteenthScreen(Calculator calculator) {
        super(calculator);
        manager = new CalculatorInputManager();
        setupLayout();
    }

    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 3;

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 200;

    private static final int HORIZONTAL_GAP = 66;
    private static final int VERTICAL_GAP = 56;

    private static final Button buttonLayout[][] = new Button[][]{
            new Button[] {Button.Sixteenths.ONE, Button.OtherFractions.ONE_EIGHTH, Button.Sixteenths.THREE},
            new Button[] {Button.OtherFractions.ONE_QUARTER, Button.Sixteenths.FIVE, Button.OtherFractions.THREE_EIGHTHS},
            new Button[] {Button.Sixteenths.SEVEN, Button.OtherFractions.ONE_HALF, Button.Sixteenths.NINE},
            new Button[] {Button.OtherFractions.FIVE_EIGHTHS, Button.Sixteenths.ELEVEN, Button.OtherFractions.THREE_QUARTERS},
            new Button[] {Button.Sixteenths.THIRTEEN, Button.OtherFractions.SEVEN_EIGHTHS, Button.Sixteenths.FIFTEEN}
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
        g.clear(0xffffff);
        g.drawPixmap(Assets.sixteenths_screen, 0, 0);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidBackButton() {
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }


}

*/