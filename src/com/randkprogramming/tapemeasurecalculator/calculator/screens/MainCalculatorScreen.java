package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.randkprogramming.tapemeasurecalculator.impl.AndroidFastRenderView;
import com.randkprogramming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input.TouchEvent;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;
import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.*;

import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.List;
import java.util.Scanner;

public class MainCalculatorScreen extends Screen {

    //--------------------------
    // Fields
    //--------------------------
    private CalculatorInputManager manager = new CalculatorInputManager();

    private Paint paintEquation = new Paint();
    private Paint paintAnswer = new Paint();
    private Paint paintEquationSmall = new Paint();
    private Paint paintAnswerSmall = new Paint();
    private static final Typeface equationFont = Typeface.create("DEFAULT_BOLD", Typeface.BOLD);

    private float debugTimer = 0;

    //--------------------------
    // Constructor
    //--------------------------
    public MainCalculatorScreen(Calculator calculator) {
        super(calculator);
        paintEquation.setTypeface(equationFont);
        paintEquation.setTextSize(75);

        paintAnswer.setTypeface(equationFont);
        paintAnswer.setTextSize(75);
        paintAnswer.setColor(Color.RED);

        paintEquationSmall.setTypeface(equationFont);
        paintEquationSmall.setTextSize(50);

        paintAnswerSmall.setTypeface(equationFont);
        paintAnswerSmall.setTextSize(50);
        paintAnswerSmall.setColor(Color.RED);
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

        g.clear(0xffffff);
        g.drawPixmap(Assets.main_calculator, 0, 0);
        drawEquation(g);

        g.drawPixmap(Assets.fractionOrDecimal[CalcState.fractionOrDecimal.ordinal()], 68, 1135);
        if(CalcState.fractionOrDecimal == DisplayModes.FractionOrDecimal.FRACTION_OPTION) {
            g.drawPixmap(Assets.fractionPrecision[CalcState.fractionPrecision.ordinal()], 251, 1135);
        }
        g.drawPixmap(Assets.units[CalcState.displayUnits.ordinal()], 434, 1135);
    }

    public static final int[] yCoordsSmallText = {75,135,195,255};
    public void drawEquation(Graphics g) {

        String equation = CalcState.equation.getString();

        if(equation.length() <= MAX_DIGITS_BIG_FONT) {
            g.drawString(equation, 40, 100, paintEquation);
        }
        else {

            if(equation.length() <= MAX_DIGITS_SMALL_FONT) {
                g.drawString(equation, 28, 75, paintEquationSmall);
            }
            else {

                Scanner s = new Scanner(equation);
                String[] lines = new String[4];

                for(int i = 0; i < lines.length; i++) {
                    lines[i] = parseNextLine(s);
                }

                for(int i = 0; i < yCoordsSmallText.length; i++) {
                    g.drawString(lines[i], 28, yCoordsSmallText[i], paintEquationSmall);
                }
            }
        }

    }

    public static final int MAX_DIGITS_BIG_FONT = 20;
    public static final int MAX_DIGITS_SMALL_FONT = 31;
    private String next = "";
    private String parseNextLine(Scanner s) {

        // Grab what was leftover from the previous line.
        String result = next;
        next = "";

        // Add as much as possible to this line
        while(s.hasNext() && result.length() <= MAX_DIGITS_SMALL_FONT) {

            next = s.next();
            if(result.length() + next.length() <= MAX_DIGITS_SMALL_FONT) {
                result += next;
                result += " ";
                next = "";
            }
            else return result;
        }
        return result;
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

    // Half the gap on one side plus half the gap on the other to allow for bigger touch areas.
    private static final int TOUCH_WIDTH = BUTTON_WIDTH + HORIZONTAL_GAP;
    private static final int TOUCH_HEIGHT = BUTTON_HEIGHT + VERTICAL_GAP;

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

    /** Calculates the top left x and y coordinates for each button. This method makes adjustments so that
     * touch events pressed within gap areas will go to the closest button. */
    public static void setupLayout() {

        for (int col = 0; col < NUM_COLS; col++) {
            xCoords[col] = (X_OFFSET + HORIZONTAL_GAP + col * (BUTTON_WIDTH + HORIZONTAL_GAP)) - (HORIZONTAL_GAP / 2);
        }
        for (int row = 0; row < NUM_ROWS; row++) {
            yCoords[row] = (Y_OFFSET + VERTICAL_GAP + row * (BUTTON_HEIGHT + VERTICAL_GAP)) - (VERTICAL_GAP / 2);
        }
        // Last row is offset more pixels
        yCoords[NUM_ROWS - 1] += 80; // From 1055 to 1135
    }

    /** Checks to see if the event fired is in bounds of a button. If it is, then it lets
     * the manager know which button was pressed or released.
     * @param event The event being fired. */
    private void checkTouchEvent(TouchEvent event) {

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                if (touchIsInBounds(event, xCoords[col], yCoords[row], TOUCH_WIDTH, TOUCH_HEIGHT)) {
                    switch (event.type) {
                        case TouchEvent.TOUCH_DOWN:    manager.setButtonPressed(buttonLayout[row][col]); break;
                        case TouchEvent.TOUCH_UP:      manager.setButtonReleased(buttonLayout[row][col]); break;
                        case TouchEvent.TOUCH_DRAGGED: break;
                    }
                    return; // Since no buttons overlap, we return
                }
            }
        }

        // If user touches equation screen...
        if (touchIsInBounds(event,0,0,800,300)) {

            if (event.type == TouchEvent.TOUCH_DOWN) {
                calculator.setScreen(new HistoryScreen(calculator));
            }
        }
        if (touchIsInBounds(event, 435, 910, 115, 115)) {
            calculator.setScreen(new FractionThirtysecondScreen(calculator));
        }
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}

    @Override public void androidBackButton() {
        System.exit(0);
    }
}
