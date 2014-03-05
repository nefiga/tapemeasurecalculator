package com.randkprogramming.tapemeasurecalculator.calculator.screens;

import android.graphics.Paint;
import android.graphics.Typeface;
import com.randkprogramming.tapemeasurecalculator.interfaces.Calculator;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Input;
import com.randkprogramming.tapemeasurecalculator.interfaces.Screen;

import java.util.List;

public class TabInfoScreen extends Screen {

    private Paint paint = new Paint();
    private static final Typeface font = Typeface.create("DEFAULT_BOLD", Typeface.BOLD);

    public TabInfoScreen(Calculator calculator) {
        super(calculator);
        paint.setTypeface(font);
        paint.setTextSize(40);
    }

    public boolean touchIsInBounds(Input.TouchEvent event, int x, int y, int width, int height) {
        return (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1);
    }

    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = calculator.getInput().getTouchEvent();
        for (Input.TouchEvent event : touchEvents) {
            if (event.type == Input.TouchEvent.TOUCH_DOWN) {
                if (touchIsInBounds(event, 100, 100, 200, 200)) {
                    calculator.setScreen(new TabSettingScreen(calculator));
                }
            }
        }
    }

    public void present(float deltaTime) {
        Graphics g = calculator.getGraphics();
        g.clear(0xffffff);
        g.drawString("Info Screen", 100, 100, paint);
    }
    public void pause() {}
    public void resume() {}
    public void dispose() {}
    public void androidBackButton() {
        calculator.setScreen(new MainCalculatorScreen(calculator));
    }
}
