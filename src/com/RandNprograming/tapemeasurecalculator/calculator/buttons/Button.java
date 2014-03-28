package com.RandNprograming.tapemeasurecalculator.calculator.buttons;

import com.RandNprograming.tapemeasurecalculator.interfaces.Input;
import com.RandNprograming.tapemeasurecalculator.interfaces.Pixmap;
import com.RandNprograming.tapemeasurecalculator.interfaces.Screen;

public class Button {

    private final int DEFAULT_WIDTH = 115;
    private final int DEFAULT_HEIGHT = 115;
    private final int DEFAULT_EXTRA_TOUCH_WIDTH = 34;
    private final int DEFAULT_EXTRA_TOUCH_HEIGHT = 15;

    //----------------------------------
    // Fields
    //----------------------------------
    private int x,y,width,height;
    private int touch_x,touch_y,touch_width,touch_height;
    private boolean pressedDown = false;
    private ButtonAction action = null;

    //----------------------------------
    // Getters
    //----------------------------------
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getTouch_x() { return touch_x; }
    public int getTouch_y() { return touch_y; }
    public int getTouch_width() { return touch_width; }
    public int getTouch_height() { return touch_height; }
    public boolean isPressedDown() { return pressedDown; }
    public ButtonAction getAction() { return action; }
    public Pixmap getIconPressed() { return action.getIconPressed(); }

    //----------------------------------
    // Setters
    //----------------------------------
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setTouch_x(int touch_x) { this.touch_x = touch_x; }
    public void setTouch_y(int touch_y) { this.touch_y = touch_y; }
    public void setTouch_width(int touch_width) { this.touch_width = touch_width; }
    public void setTouch_height(int touch_height) { this.touch_height = touch_height; }
    public void setPressedDown(boolean down) { this.pressedDown = down; }
    public void setAction(ButtonAction action) { this.action = action; }

    //----------------------------------
    // Constructors
    //----------------------------------
    public Button(int x, int y, ButtonAction action) {
        this.x = x;
        this.y = y;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.touch_x = x - DEFAULT_EXTRA_TOUCH_WIDTH;
        this.touch_y = y - DEFAULT_EXTRA_TOUCH_HEIGHT;
        this.touch_width = DEFAULT_WIDTH + DEFAULT_EXTRA_TOUCH_WIDTH * 2;
        this.touch_height = DEFAULT_HEIGHT + DEFAULT_EXTRA_TOUCH_HEIGHT * 2;
        this.action = action;
    }
    public Button(int x, int y, int width, int height, int touch_x, int touch_y, int touch_width, int touch_height,
            ButtonAction action) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.touch_x = touch_x;
        this.touch_y = touch_y;
        this.touch_width = touch_width;
        this.touch_height = touch_height;
        this.action = action;
    }

    //----------------------------------
    // In Bounds
    //----------------------------------
    public boolean inBounds(Input.TouchEvent event) {
        return Screen.touchIsInBounds(event,touch_x,touch_y,touch_width,touch_height);
    }

    //----------------------------------
    // Perform Action
    //----------------------------------
    public void performAction() {
        if(action != null)
            action.performAction();
    }

}