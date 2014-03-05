package com.randkprogramming.tapemeasurecalculator.calculator.assets;

import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.DisplayModes;
import com.randkprogramming.tapemeasurecalculator.impl.AndroidFastRenderView;
import com.randkprogramming.tapemeasurecalculator.interfaces.Graphics;
import com.randkprogramming.tapemeasurecalculator.interfaces.Pixmap;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.Button;

public class Assets {

    public static Pixmap main_calculator;
    public static Pixmap[] fractionOrDecimal = new Pixmap[2];
    public static Pixmap[] fractionPrecision = new Pixmap[3];
    public static Pixmap[] units = new Pixmap[2];
    public static Pixmap history;
    public static Pixmap thirtyseconds_screen;
    public static Pixmap sixteenths_screen;

    public static void loadImages() {

        Graphics g = AndroidFastRenderView.getCalculator().getGraphics();
        main_calculator = g.newPixmap("square_colored.png", Graphics.PixmapFormat.RGB565);
        thirtyseconds_screen = g.newPixmap("32nds_screen.png", Graphics.PixmapFormat.RGB565);
        sixteenths_screen = g.newPixmap("16ths_screen.png", Graphics.PixmapFormat.RGB565);

        fractionOrDecimal[0] = g.newPixmap("fraction_option.png", Graphics.PixmapFormat.RGB565);
        fractionOrDecimal[1] = g.newPixmap("decimal_option.png", Graphics.PixmapFormat.RGB565);

        fractionPrecision[0] = g.newPixmap("16ths.png", Graphics.PixmapFormat.RGB565);
        fractionPrecision[1] = g.newPixmap("32nds.png", Graphics.PixmapFormat.RGB565);
        fractionPrecision[2] = g.newPixmap("64ths.png", Graphics.PixmapFormat.RGB565);

        units[0] = g.newPixmap("inches_only.png", Graphics.PixmapFormat.RGB565);
        units[1] = g.newPixmap("feet_and_inches.png", Graphics.PixmapFormat.RGB565);

        Assets.history = g.newPixmap("history.png", Graphics.PixmapFormat.RGB565);
    }

}
