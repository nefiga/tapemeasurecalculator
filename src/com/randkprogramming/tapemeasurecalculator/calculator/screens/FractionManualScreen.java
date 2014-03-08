package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import android.graphics.Paint;
import android.graphics.Typeface;
import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;
import com.randkprogramming.tapemeasurecalculator.calculator.buttons.Button;
import com.randkprogramming.tapemeasurecalculator.calculator.buttons.ButtonLayout;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.randkprogramming.tapemeasurecalculator.impl.AndroidFastRenderView;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalculatorInputManager;

import java.util.List;

public class FractionManualScreen extends Screen {


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
        else if (fraction.length() >= 1 && fraction.contains("/") && fraction.codePointCount(fraction.indexOf("/"), fraction.length()) <= 2) {
            addDenominator(number);
        }
    }

    public static void addNumerator(String n) {
        fraction += n;
    }

    public static void addDenominator(String d) {

        // Don't allow 0 in denominator unless preceded by another number
        if(d.equals("0") && fraction.contains("/") && fraction.lastIndexOf('/') == fraction.length()-1) {
            return;
        }

        fraction += d;
    }

    public static void enter() {

        // Have to have a complete fraction to hit enter
        if( ! fraction.contains("/") || fraction.lastIndexOf('/') == fraction.length()-1) {
            return;
        }

        CalcState.addFraction(fraction);
        clear();
        Calculator c = AndroidFastRenderView.getCalculator();
        c.setScreen(new MainCalculatorScreen(c));
    }

    @Override public void update(float deltaTime) {

        List<Input.TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (Input.TouchEvent event : touchEvents) {

            checkTouchEvent(event);
        }
    }

    public void checkTouchEvent(Input.TouchEvent event) {

        for(int i = 0; i < ButtonLayout.fractionScreenButtons.size(); i++) {

            Button button = ButtonLayout.fractionScreenButtons.get(i);

            switch (event.type) {
                case Input.TouchEvent.TOUCH_DOWN: {
                    if(button.inBounds(event)) {
                        manager.onTouchDown(event, button);
                        return;
                    }
                    break;
                }
                case Input.TouchEvent.TOUCH_UP:      manager.onLift(event); break;
                case Input.TouchEvent.TOUCH_DRAGGED: manager.onMovement(event); break;
            }
        }
    }

    @Override public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.clear(0xffffff);
        g.drawPixmap(Assets.fraction_screen, 0, 0);

        paintText(g);
        paintPressedButtons(g);
    }

    public void paintText(Graphics g) {

        float offset = paint.measureText(fraction) / 2;
        float x_position = 400 - offset;
        g.drawString(fraction, x_position, 220, paint);
    }

    public void paintPressedButtons(Graphics g) {

        for(Button b : manager.getPressedButtons().values()) {
            if(b.isPressedDown()) {
                g.drawPixmap(b.getIconPressed(), b.getX(), b.getY());
            }
        }
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}

    @Override public void androidBackButton() {
        clear();
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
