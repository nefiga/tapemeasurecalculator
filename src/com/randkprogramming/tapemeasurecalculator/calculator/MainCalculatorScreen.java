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

    CalculatorInputManager manager;
    CalculateEquation equation;
    ButtonActions buttonActions;
    FractionActions fractionActions;
    Paint paint;
    Typeface tf;

    public MainCalculatorScreen(Calculator calculator) {
        super(calculator);
        buttonActions = new ButtonActions();
        equation = new CalculateEquation(buttonActions);
        fractionActions = new FractionActions();
        manager = new CalculatorInputManager(equation, buttonActions, fractionActions);
        tf = Typeface.create("DEFAULT_BOLD", Typeface.BOLD);
        paint = new Paint();
        paint.setTypeface(tf);
        paint.setTextSize(50);
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
                if (touchIsInBounds(event, 10, 225, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.SEVEN);
                }
                if (touchIsInBounds(event, 170, 225, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.EIGHT);
                }
                if (touchIsInBounds(event, 330, 225, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.NINE);
                }
                if (touchIsInBounds(event, 490, 225, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.BACKSPACE);
                }
                if (touchIsInBounds(event, 650, 225, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.CLEAR);
                }

                if (touchIsInBounds(event, 10, 440, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.FOUR);
                }
                if (touchIsInBounds(event, 170, 440, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.FIVE);
                }
                if (touchIsInBounds(event, 330, 440, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.SIX);
                }
                if (touchIsInBounds(event, 490, 440, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.PLUS);
                }
                if (touchIsInBounds(event, 650, 440, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.TIMES);
                }

                if (touchIsInBounds(event, 10, 650, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.ONE);
                }
                if (touchIsInBounds(event, 170, 650, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.TWO);
                }
                if (touchIsInBounds(event, 330, 650, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.THREE);
                }
                if (touchIsInBounds(event, 490, 650, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.MINUS);
                }
                if (touchIsInBounds(event, 650, 650, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.DIVIDE);
                }

                if (touchIsInBounds(event, 10, 865, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.DECIMAL_POINT);
                }
                if (touchIsInBounds(event, 170, 865, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.ZERO);
                }
                if (touchIsInBounds(event, 330, 865, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.EQUALS);
                }
                if (touchIsInBounds(event, 490, 865, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.FEET);
                }
                if (touchIsInBounds(event, 650, 865, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.INCHES);
                }

                if (touchIsInBounds(event, 650, 865, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.FRACTION_BUTTONS);
                }
                if (touchIsInBounds(event, 650, 865, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.FRACTION_CHANGE_BUTTON);
                }
                if (touchIsInBounds(event, 650, 865, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.DISPLAY_CHANGE_BUTTON);
                }
                if (touchIsInBounds(event, 650, 865, 140, 190)) {
                    manager.setButtonPressed(CalculatorButtons.INFO);
                }


                //Called when you release your finger
                if (event.type == TouchEvent.TOUCH_UP) {
                    if (touchIsInBounds(event, 10, 225, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.SEVEN);
                    }
                    if (touchIsInBounds(event, 170, 225, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.EIGHT);
                    }
                    if (touchIsInBounds(event, 330, 225, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.NINE);
                    }
                    if (touchIsInBounds(event, 490, 225, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.BACKSPACE);
                    }
                    if (touchIsInBounds(event, 650, 225, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.CLEAR);
                    }

                    if (touchIsInBounds(event, 10, 440, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.FOUR);
                    }
                    if (touchIsInBounds(event, 170, 440, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.FIVE);
                    }
                    if (touchIsInBounds(event, 330, 440, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.SIX);
                    }
                    if (touchIsInBounds(event, 490, 440, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.PLUS);
                    }
                    if (touchIsInBounds(event, 650, 440, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.TIMES);
                    }

                    if (touchIsInBounds(event, 10, 650, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.ONE);
                    }
                    if (touchIsInBounds(event, 170, 650, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.TWO);
                    }
                    if (touchIsInBounds(event, 330, 650, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.THREE);
                    }
                    if (touchIsInBounds(event, 490, 650, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.MINUS);
                    }
                    if (touchIsInBounds(event, 650, 650, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.DIVIDE);
                    }

                    if (touchIsInBounds(event, 10, 865, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.DECIMAL_POINT);
                    }
                    if (touchIsInBounds(event, 170, 865, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.ZERO);
                    }
                    if (touchIsInBounds(event, 330, 865, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.EQUALS);
                    }
                    if (touchIsInBounds(event, 490, 865, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.FEET);
                    }
                    if (touchIsInBounds(event, 650, 865, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.INCHES);
                    }

                    if (touchIsInBounds(event, 650, 865, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.FRACTION_BUTTONS);
                    }
                    if (touchIsInBounds(event, 650, 865, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.FRACTION_CHANGE_BUTTON);
                    }
                    if (touchIsInBounds(event, 650, 865, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.DISPLAY_CHANGE_BUTTON);
                    }
                    if (touchIsInBounds(event, 650, 865, 140, 190)) {
                        manager.setButtonReleased(CalculatorButtons.INFO);
                    }
                }
            }

            manager.update(deltaTime);
        }
    }

    @Override
    public void present(float deltaTime) {
        //Draw Images
        Graphics g = calculator.getGraphics();

        g.drawPixmap(Assets.main_calculator, 0, 0);
        g.drawString(equation.getEquation(), 10, 50, paint);
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
