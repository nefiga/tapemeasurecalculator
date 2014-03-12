package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.Scanner;

public class PaintEquation {

    private static final int BIG_FONT = 75;
    private static final int SMALL_FONT = 50;

    private static final int BIG_FONT_THRESHOLD = 15; // Number of digits allowed before changing to small font
    private static final int SMALL_FONT_THRESHOLD = 26; // Number of digits allowed before moving to next line

    private Paint paint = new Paint();
    private boolean multipleLines = false;
    private String[] lines = new String[] {"","","",""};
    private float[] xCoords = new float[] {0,0,0,0};

    public Paint getPaint() { return this.paint; }
    public boolean hasMultipleLines() { return this.multipleLines; }
    public String[] getLines() { return this.lines; }
    public float[] getXCoords() { return xCoords; }

    public PaintEquation() {
        paint.setTypeface(Typeface.create("DEFAULT_BOLD", Typeface.BOLD));
    }

    public void update(String equation) {

        if(equation.length() > SMALL_FONT_THRESHOLD) {

            multipleLines = true;
            paint.setTextSize(SMALL_FONT);
            Scanner s = new Scanner(equation);

            for(int i = 0; i < lines.length; i++) {
                lines[i] = parseNextLine(s);
            }

            for(int i = 0; i < lines.length; i++) {
                xCoords[i] = 400 - (paint.measureText(lines[i]) / 2);
            }

        }
        else {

            if(equation.length() > BIG_FONT_THRESHOLD) {
                paint.setTextSize(SMALL_FONT);
            }
            else {
                paint.setTextSize(BIG_FONT);
            }

            xCoords[0] = 400 - (paint.measureText(equation) / 2);
            xCoords[1] = 0;
            xCoords[2] = 0;
            multipleLines = false;
        }
    }

    private String next = "";
    private String parseNextLine(Scanner s) {

        // Grab what was leftover from the previous line.
        String result = next;
        next = "";

        // Add as much as possible to this line
        while(s.hasNext() && result.length() <= SMALL_FONT_THRESHOLD) {

            next = s.next();
            if(result.length() + next.length() <= SMALL_FONT_THRESHOLD) {
                result += next;
                result += " ";
                next = "";
            }
            else return result;
        }
        return result;
    }
}
