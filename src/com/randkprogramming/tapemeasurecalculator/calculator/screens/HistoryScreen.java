package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.randkprogramming.tapemeasurecalculator.calculator.assets.Assets;
import com.randkprogramming.tapemeasurecalculator.calculator.buttons.Button;
import com.randkprogramming.tapemeasurecalculator.calculator.buttons.ButtonLayout;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.*;
import com.randkprogramming.tapemeasurecalculator.calculator.utilities.ParserConverter;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;

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
        for (Input.TouchEvent event : touchEvents) {
            checkBounds(event);
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

        Button save = ButtonLayout.historyScreenButtons.get(0);
        Button enter = ButtonLayout.historyScreenButtons.get(1);

        g.drawPixmap(Assets.history_screen_buttons[0], save.getX(), save.getY());
        g.drawPixmap(Assets.history_screen_buttons[1], enter.getX(), enter.getY());

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
                g.drawString(equation.getString(), 30, yCoords[i] + 64, paint);
                float offset = paint.measureText(equation.getString());
                g.drawString(" = " + ParserConverter.formatToString(equation.getResult()), 30 + offset, yCoords[i] + 64, answerPaint);
            }
            i++;
        }
    }

    public void drawSelection(Graphics g) {

        if(CalcHistory.selectedIndex < 0 || CalcHistory.selectedIndex > CalcHistory.MAX_ENTRIES) {
            return;
        }

        int highlight = Color.argb(64,0,0,255);
        g.drawRect(20,yCoords[CalcHistory.selectedIndex],762,96,highlight);
    }

    private static int[] yCoords = {212,319,425,532,639,746,853,959,1066,1173};
    public void checkBounds(Input.TouchEvent event) {

        Button save = ButtonLayout.historyScreenButtons.get(0);
        Button enter = ButtonLayout.historyScreenButtons.get(1);

        checkButtonBounds(save,event);
        checkButtonBounds(enter,event);

        for(int i = 0; i < 10; i++) {

            if(touchIsInBounds(event, 0, yCoords[i], 800, 95) && event.type == Input.TouchEvent.TOUCH_DOWN) {
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
    @Override public void androidBackButton() {

        CalcHistory.selectedIndex = -1;
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }

}
