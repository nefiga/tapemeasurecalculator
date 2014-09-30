package com.RandNprograming.tapemeasurecalculator.calculator.screens;

import android.graphics.Color;
import android.graphics.Paint;
import com.RandNprograming.tapemeasurecalculator.calculator.buttons.Button;
import com.RandNprograming.tapemeasurecalculator.calculator.buttons.ButtonLayout;
import com.RandNprograming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Input.TouchEvent;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;
import com.RandNprograming.tapemeasurecalculator.calculator.assets.Assets;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.*;

import java.util.List;

public class MainCalculatorScreen extends Screen {

    //--------------------------
    // Fields
    //--------------------------
    private CalculatorInputManager manager = new CalculatorInputManager();
    private float debugTimer = 0;
    private final int LEFT_INCH = 75, RIGHT_INCH = 725;
    Paint paint;

    //--------------------------
    // Constructor
    //--------------------------
    public MainCalculatorScreen(Calculator calculator) {
        super(calculator);
        paint = new Paint();
        paint.setTextSize(34);
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
        for (int i = 0; i < touchEvents.size(); i++) {

            TouchEvent event = touchEvents.get(i);
            if (event != null) {
                checkTouchEvent(event);
            }
        }
    }

    @Override
    public void present(float deltaTime) {

        // Draw Images
        Graphics g = calculator.getGraphics();

        g.clear(0xffffff);
        g.drawPixmap(Assets.main_screen, 0, 0);
        g.drawPixmap(Assets.menu_button, 710, 35);

        if (CalcState.displayTapeImage && CalcState.displayingAnswer && !CalcState.paint.hasMultipleLines()) {
            drawTapeImage(g);
        }

        drawEquation(g);

        g.drawPixmap(Assets.fractionOrDecimal[CalcState.fractionOrDecimal.ordinal()], 68, 1135);
        if (CalcState.fractionOrDecimal == DisplayModes.FractionOrDecimal.FRACTION_OPTION) {
            g.drawPixmap(Assets.fractionPrecision[CalcState.fractionPrecision.ordinal()], 251, 1135);
        }
        g.drawPixmap(Assets.units[CalcState.displayUnits.ordinal()], 434, 1135);

        drawPressedButtons(g);
    }

    private void drawTapeImage(Graphics g) {

        if (CalcState.equation.getResult() == null) return;

        g.drawPixmap(Assets.tapeMeasureHelper, 20, 30);

        int number = CalcState.equation.getResult().intValue();
        double decimal = CalcState.equation.getResult() - number;

        int lineX = LEFT_INCH + (int) (648 * decimal);
        String leftInch = "", rightInch = "";
        leftInch += number;
        number++;
        rightInch += number;
        int leftInchOffset = (int) paint.measureText(leftInch) /2;
        int rightInchOffset = (int) paint.measureText(rightInch) /2;

        g.drawLine(lineX, 30, lineX, 170, Color.RED);
        g.drawLine(lineX + 1, 30, lineX + 1, 170, Color.RED);
        g.drawString(leftInch, LEFT_INCH - leftInchOffset, 157, paint);
        g.drawString(rightInch, RIGHT_INCH - rightInchOffset, 157, paint);
    }

    public void drawPressedButtons(Graphics g) {

        for (Button b : manager.getPressedButtons().values()) {
            if (b.isPressedDown()) {
                g.drawPixmap(b.getIconPressed(), b.getX(), b.getY());
            }
        }
    }

    public static final int[] yCoordsMultiple = {75, 135, 195, 255};

    public void drawEquation(Graphics g) {

        String equation = CalcState.equation.getString();
        PaintEquation p = CalcState.paint;

        if (CalcState.displayTapeImage && CalcState.displayingAnswer && p.hasMultipleLines()) {
            g.drawString("Error!", 350, 160, paint);
            g.drawString("Answer is to large for tape measure", 130, 195, paint);
            return;
        }

        if (p.hasMultipleLines()) {
            for (int i = 0; i < 4; i++) {

                String line = p.getLines()[i];

                float xPos = p.getXCoords()[i];
                String s = line;

                while (s.contains("ft") || s.contains("in")) {

                    int ftIndex = s.indexOf("ft");
                    int inIndex = s.indexOf("in");

                    int index;

                    // Find out which comes first, ft or in
                    if (ftIndex < 0) {
                        index = inIndex;
                    } else if (inIndex < 0) {
                        index = ftIndex;
                    } else {
                        index = ftIndex < inIndex ? ftIndex : inIndex;
                    }

                    // skip the word ft, or in
                    index += 2;

                    String first = s.substring(0, index);
                    String exponent = s.substring(index, index + 1);
                    s = s.substring(index + 1);

                    g.drawString(first, xPos, yCoordsMultiple[i], p.getPaint());

                    xPos += p.getPaint().measureText(first);

                    g.drawString(exponent, xPos, yCoordsMultiple[i] - p.getPaint().getTextSize() / 3, p.getPaint());

                    xPos += p.getPaint().measureText(exponent);
                }

                g.drawString(s, xPos, yCoordsMultiple[i], p.getPaint());
            }
        } else {

            float xPos = p.getXCoords()[0];
            String s = equation;

            while (s.contains("ft") || s.contains("in")) {

                int ftIndex = s.indexOf("ft");
                int inIndex = s.indexOf("in");

                int index;

                // Find out which comes first, ft or in
                if (ftIndex < 0) {
                    index = inIndex;
                } else if (inIndex < 0) {
                    index = ftIndex;
                } else {
                    index = ftIndex < inIndex ? ftIndex : inIndex;
                }

                // skip the word ft, or in
                index += 2;

                String first = s.substring(0, index);
                String exponent = s.substring(index, index + 1);
                s = s.substring(index + 1);

                if (CalcState.displayTapeImage && CalcState.displayingAnswer)g.drawString(first, xPos, 260, p.getPaint());
                else g.drawString(first, xPos, 180, p.getPaint());

                xPos += p.getPaint().measureText(first);

                if (CalcState.displayTapeImage && CalcState.displayingAnswer)g.drawString(exponent, xPos, 260 - p.getPaint().getTextSize() / 3, p.getPaint());
                else g.drawString(exponent, xPos, 180 - p.getPaint().getTextSize() / 3, p.getPaint());

                xPos += p.getPaint().measureText(exponent);
            }

            if (CalcState.displayTapeImage && CalcState.displayingAnswer) g.drawString(s, xPos, 245, p.getPaint());
            else g.drawString(s, xPos, 180, p.getPaint());
        }
    }

    /**
     * Checks to see if the event fired is in bounds of a button. If it is, then it lets
     * the manager know which button was pressed or released.
     *
     * @param event The event being fired.
     */
    private void checkTouchEvent(TouchEvent event) {

        switch (event.type) {
            case TouchEvent.TOUCH_DOWN: {
                for (int i = 0; i < ButtonLayout.mainScreenButtons.size(); i++) {

                    Button button = ButtonLayout.mainScreenButtons.get(i);

                    // Don't press fraction precision button on decimal mode
                    if (CalcState.fractionOrDecimal == DisplayModes.FractionOrDecimal.DECIMAL_OPTION &&
                            (button.getIconPressed() == Assets.pressed_buttons_special[2] ||
                                    button.getIconPressed() == Assets.pressed_buttons_special[3] ||
                                    button.getIconPressed() == Assets.pressed_buttons_special[4])) {
                        continue;
                    }

                    if (button.inBounds(event)) {
                        manager.onTouchDown(event, button);
                        return;
                    }
                }
                break;
            }
            case TouchEvent.TOUCH_UP:
                manager.onLift(event);
                break;
            case TouchEvent.TOUCH_DRAGGED:
                manager.onMovement(event);
                break;
        }

        // If user touches equation screen...
        if (touchIsInBounds(event, 0, 0, 680, 300)) {
            if (event.type == TouchEvent.TOUCH_DOWN) {
                calculator.setScreen(new HistoryScreen(calculator));
            }
        }
        if (touchIsInBounds(event, 710, 35, 65, 133)) {
            calculator.setScreen(new SettingScreen(calculator));
        }
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
    public void androidBackButton(AndroidTapemeasureCalculator activity) {
        activity.onPause();
        activity.onDestroy();
        activity.finish();
    }

    @Override
    public void androidOptionButton() {
        calculator.setScreen(new SettingScreen(calculator));
    }
}
