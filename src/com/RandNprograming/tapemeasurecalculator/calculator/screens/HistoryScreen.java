package com.RandNprograming.tapemeasurecalculator.calculator.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.RandNprograming.tapemeasurecalculator.calculator.assets.Assets;
import com.RandNprograming.tapemeasurecalculator.calculator.buttons.Button;
import com.RandNprograming.tapemeasurecalculator.calculator.buttons.ButtonLayout;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.*;
import com.RandNprograming.tapemeasurecalculator.calculator.utilities.ParserConverter;
import com.RandNprograming.tapemeasurecalculator.impl.AndroidTapemeasureCalculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Input;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;

import java.util.List;

public class HistoryScreen extends Screen {

    private CalculatorInputManager manager = new CalculatorInputManager();
    private Paint paint = new Paint();
    private Paint answerPaint = new Paint();
    private static final Typeface historyFont = Typeface.create("DEFAULT_BOLD", Typeface.BOLD);

    public HistoryScreen(Calculator calculator) {
        super(calculator);
        paint.setTypeface(historyFont);
        paint.setTextSize(50);
        answerPaint.setTypeface(historyFont);
        answerPaint.setColor(Color.RED);
        answerPaint.setTextSize(50);
    }

    @Override public void update(float deltaTime) {

        List<Input.TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (int i = 0; i < touchEvents.size(); i++) {

            Input.TouchEvent event = touchEvents.get(i);
            if(event != null) {
                checkBounds(event);
            }
        }
    }
    @Override public void present(float deltaTime) {

        Graphics g = calculator.getGraphics();
        g.clear(0xffffff);
        g.drawPixmap(Assets.history_screen, 0, 0);
        drawButtons(g);
        drawEquations(g);
        drawSelection(g);
    }

    public void drawButtons(Graphics g) {

        if(CalcHistory.selectedIndex < 0) {
            return;
        }

        Button use_equation = ButtonLayout.historyScreenButtons.get(0);
        Button use_answer = ButtonLayout.historyScreenButtons.get(1);

        g.drawPixmap(Assets.history_screen_buttons[0], use_equation.getX(), use_equation.getY());
        g.drawPixmap(Assets.history_screen_buttons[1], use_answer.getX(), use_answer.getY());

        for(Button b : manager.getPressedButtons().values()) {
            if(b.isPressedDown()) {
                g.drawPixmap(b.getIconPressed(), b.getX(), b.getY());
            }
        }
    }

    public void drawEquations(Graphics g) {

        int i = 0;
        for(Equation equation : CalcHistory.getHistory()) {

            if( i < yCoords.length) {

                String result = " = " + ParserConverter.formatToString(equation.getResult(), equation.getUnitDimension());
                int yOffset = 64;

                if(equation.getString().length() < 28) {
                    paint.setTextSize(50);
                    answerPaint.setTextSize(50);
                }
                else if(equation.getString().length() < 40) {
                    paint.setTextSize(36);
                    answerPaint.setTextSize(36);
                }
                else {
                    paint.setTextSize(25);
                    answerPaint.setTextSize(25);
                    yOffset -= 10;
                }

                float offset = paint.measureText(equation.getString());
                g.drawString(equation.getString(), 30, yCoords[i] + yOffset, paint);
                g.drawString(result, 30 + offset, yCoords[i] + yOffset, answerPaint);

            }
            i++;
        }
    }

    public void drawSelection(Graphics g) {

        if(CalcHistory.selectedIndex < 0 || CalcHistory.selectedIndex > CalcHistory.MAX_ENTRIES) {
            return;
        }

        int highlight = Color.argb(64,0,0,255);
        g.drawRect(20,yCoords[CalcHistory.selectedIndex],762,106,highlight);
    }

    private static int[] yCoords = {207,313,419,525,631,737,843,949,1055,1161};
    public void checkBounds(Input.TouchEvent event) {

        Button use_equation = ButtonLayout.historyScreenButtons.get(0);
        Button use_answer = ButtonLayout.historyScreenButtons.get(1);

        checkButtonBounds(use_equation,event);
        checkButtonBounds(use_answer,event);

        for(int i = 0; i < 10; i++) {

            if(touchIsInBounds(event, 0, yCoords[i], 800, 106) && event.type == Input.TouchEvent.TOUCH_DOWN) {
                selectEquation(i);
            }
        }

    }

    private void checkButtonBounds(Button button, Input.TouchEvent event) {

        switch (event.type) {
            case Input.TouchEvent.TOUCH_DOWN: {
                if(button.inBounds(event)) {
                    manager.onTouchDown(event, button);
                }
                break;
            }
            case Input.TouchEvent.TOUCH_UP:      manager.onLift(event); break;
            case Input.TouchEvent.TOUCH_DRAGGED: manager.onMovement(event); break;
        }
    }

    public void selectEquation(int index) {

        Equation e = CalcHistory.getHistoryAt(index);
        if(e != null) {
            CalcHistory.selectedIndex = index;
        }
        else {
            CalcHistory.selectedIndex = -1;
        }
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void androidOptionButton() {}
    @Override public void androidBackButton(AndroidTapemeasureCalculator activity) {
        CalcHistory.selectedIndex = -1;
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }

}
