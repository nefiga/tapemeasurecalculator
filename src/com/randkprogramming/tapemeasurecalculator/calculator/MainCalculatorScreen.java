package com.randkprogramming.tapemeasurecalculator.calculator;

import com.randkprogramming.tapemeasurecalculator.Calculator;
import com.randkprogramming.tapemeasurecalculator.Graphics;
import com.randkprogramming.tapemeasurecalculator.Input.TouchEvent;
import com.randkprogramming.tapemeasurecalculator.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.*;

import java.util.List;

public class MainCalculatorScreen extends Screen {

    CalculatorInputManager manager;
    CalculateEquation equation;
    ButtonActions buttonActions;
    FractionActions fractionActions;

    public MainCalculatorScreen(Calculator calculator) {
        super(calculator);
        buttonActions = new ButtonActions();
        equation = new CalculateEquation(buttonActions);
        fractionActions = new FractionActions();
        manager = new CalculatorInputManager(equation, buttonActions, fractionActions);
    }

    float justabit = 0;
    @Override
    public void update(float deltaTime) {
        justabit += deltaTime;
        if (justabit >= 2.0f) {
            System.out.println(equation.getEquation());
            justabit = 0;
        }
        // Check for TouchEvents
        List<TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        calculator.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            //Called when you touch the screen
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (touchIsInBounds(event, 15, 107, 170, 138)) {
                    manager.setButtonPressed(CalculatorButtons.SEVEN);
                }
                if (touchIsInBounds(event, 215, 107, 170, 138)) {
                    manager.setButtonPressed(CalculatorButtons.EIGHT);
                }
                if (touchIsInBounds(event, 415, 107, 170, 138)) {
                    manager.setButtonPressed(CalculatorButtons.NINE);
                }
                if (touchIsInBounds(event, 615, 107, 170, 138)) {
                    manager.setButtonPressed(CalculatorButtons.DIVIDE);
                }
                if (touchIsInBounds(event, 15, 258, 170, 135)) {
                    manager.setButtonPressed(CalculatorButtons.FOUR);
                }
                if (touchIsInBounds(event, 215, 258, 170, 135)) {
                    manager.setButtonPressed(CalculatorButtons.FIVE);
                }
                if (touchIsInBounds(event, 415, 258, 170, 135)) {
                    manager.setButtonPressed(CalculatorButtons.SIX);
                }
                if (touchIsInBounds(event, 615, 258, 170, 135)) {
                    manager.setButtonPressed(CalculatorButtons.TIMES);
                }
                if (touchIsInBounds(event, 15, 407, 170, 136)) {
                    manager.setButtonPressed(CalculatorButtons.ONE);
                }
                if (touchIsInBounds(event, 215, 407, 170, 136)) {
                    manager.setButtonPressed(CalculatorButtons.TWO);
                }
                if (touchIsInBounds(event, 415, 407, 170, 136)) {
                    manager.setButtonPressed(CalculatorButtons.THREE);
                }
                if (touchIsInBounds(event, 615, 407, 170, 136)) {
                    manager.setButtonPressed(CalculatorButtons.MINUS);
                }
                if (touchIsInBounds(event, 15, 557, 170, 136)) {
                    manager.setButtonPressed(CalculatorButtons.DECIMAL_POINT);
                }
                if (touchIsInBounds(event, 215, 557, 170, 136)) {
                    manager.setButtonPressed(CalculatorButtons.ZERO);
                }
                if (touchIsInBounds(event, 415, 557, 170, 136)) {
                    manager.setButtonPressed(CalculatorButtons.EQUALS);
                }
                if (touchIsInBounds(event, 615, 557, 170, 136)) {
                    manager.setButtonPressed(CalculatorButtons.PLUS);
                }
                if (touchIsInBounds(event, 15, 707, 370, 136)) {
                    manager.setButtonPressed(CalculatorButtons.GO_TO_FRACTION);
                }
                if (touchIsInBounds(event, 415, 707, 370, 136)) {
                    manager.setButtonPressed(CalculatorButtons.CLEAR);
                }
                if (touchIsInBounds(event, 15, 857, 170, 136)) {
                    manager.setButtonPressed(CalculatorButtons.INFO);
                }
                if (touchIsInBounds(event, 615, 857, 170, 136)) {
                    manager.setButtonPressed(CalculatorButtons.FRACTION_CHANGE_BUTTON);
                }
            }

            //Called when you release your finger
            if (event.type == TouchEvent.TOUCH_UP) {
                if (touchIsInBounds(event, 15, 107, 170, 138)) {
                    manager.setButtonReleased(CalculatorButtons.SEVEN);
                }
                if (touchIsInBounds(event, 215, 107, 170, 138)) {
                    manager.setButtonReleased(CalculatorButtons.EIGHT);
                }
                if (touchIsInBounds(event, 415, 107, 170, 138)) {
                    manager.setButtonReleased(CalculatorButtons.NINE);
                }
                if (touchIsInBounds(event, 615, 107, 170, 138)) {
                    manager.setButtonReleased(CalculatorButtons.DIVIDE);
                }
                if (touchIsInBounds(event, 15, 258, 170, 135)) {
                    manager.setButtonReleased(CalculatorButtons.FOUR);
                }
                if (touchIsInBounds(event, 215, 258, 170, 135)) {
                    manager.setButtonReleased(CalculatorButtons.FIVE);
                }
                if (touchIsInBounds(event, 415, 258, 170, 135)) {
                    manager.setButtonReleased(CalculatorButtons.SIX);
                }
                if (touchIsInBounds(event, 615, 258, 170, 135)) {
                    manager.setButtonReleased(CalculatorButtons.TIMES);
                }
                if (touchIsInBounds(event, 15, 407, 170, 136)) {
                    manager.setButtonReleased(CalculatorButtons.ONE);
                }
                if (touchIsInBounds(event, 215, 407, 170, 136)) {
                    manager.setButtonReleased(CalculatorButtons.TWO);
                }
                if (touchIsInBounds(event, 415, 407, 170, 136)) {
                    manager.setButtonReleased(CalculatorButtons.THREE);
                }
                if (touchIsInBounds(event, 615, 407, 170, 136)) {
                    manager.setButtonReleased(CalculatorButtons.MINUS);
                }
                if (touchIsInBounds(event, 15, 557, 170, 136)) {
                    manager.setButtonReleased(CalculatorButtons.DECIMAL_POINT);
                }
                if (touchIsInBounds(event, 215, 557, 170, 136)) {
                    manager.setButtonReleased(CalculatorButtons.ZERO);
                }
                if (touchIsInBounds(event, 415, 557, 170, 136)) {
                    manager.setButtonReleased(CalculatorButtons.EQUALS);
                }
                if (touchIsInBounds(event, 615, 557, 170, 136)) {
                    manager.setButtonReleased(CalculatorButtons.PLUS);
                }
                if (touchIsInBounds(event, 15, 707, 370, 136)) {
                    manager.setButtonReleased(CalculatorButtons.GO_TO_FRACTION);
                }
                if (touchIsInBounds(event, 415, 707, 370, 136)) {
                    manager.setButtonReleased(CalculatorButtons.CLEAR);
                }
                if (touchIsInBounds(event, 15, 857, 170, 136)) {
                    manager.setButtonReleased(CalculatorButtons.INFO);
                }
                if (touchIsInBounds(event, 615, 857, 170, 136)) {
                    manager.setButtonReleased(CalculatorButtons.FRACTION_CHANGE_BUTTON);
                }
            }
        }
        manager.update(deltaTime);
    }

    @Override
    public void present(float deltaTime) {
        //Draw Images
        Graphics g = calculator.getGraphics();

        g.drawPixmap(Assets.main_calculator, 0, 0);
    }

    // Checks to see if your finger is within an area
    public boolean touchIsInBounds(TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1) return true;
        else return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void androidBackButton() {

    }
}
