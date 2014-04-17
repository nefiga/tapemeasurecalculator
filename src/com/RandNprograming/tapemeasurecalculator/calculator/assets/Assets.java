package com.RandNprograming.tapemeasurecalculator.calculator.assets;

import com.RandNprograming.tapemeasurecalculator.impl.AndroidFastRenderView;
import com.RandNprograming.tapemeasurecalculator.interfaces.Graphics;
import com.RandNprograming.tapemeasurecalculator.interfaces.Pixmap;

public class Assets {

    public static Pixmap main_screen;
    public static Pixmap fraction_screen;
    public static Pixmap history_screen;
    public static Pixmap info_screen;
    public static Pixmap settings_screen;

    public static Pixmap[] fractionOrDecimal = new Pixmap[2];
    public static Pixmap[] fractionPrecision = new Pixmap[3];
    public static Pixmap[] units = new Pixmap[2];
    public static Pixmap[] history_screen_buttons = new Pixmap[2];

    public static Pixmap[] pressed_buttons_numbers = new Pixmap[10];
    public static Pixmap[] pressed_buttons_operators = new Pixmap[4];
    public static Pixmap[] pressed_buttons_calculate = new Pixmap[7];
    public static Pixmap[] pressed_buttons_special = new Pixmap[8];
    public static Pixmap[] pressed_buttons_fractionScreen = new Pixmap[2];
    public static Pixmap[] pressed_buttons_historyScreen = new Pixmap[2];

    public static Pixmap[] orderOfOperations = new Pixmap[2];

    public static void loadImages() {

        Graphics g = AndroidFastRenderView.getCalculator().getGraphics();
        final Graphics.PixmapFormat format = Graphics.PixmapFormat.RGB565;

        main_screen = g.newPixmap("screens/main_screen.png", format);
        fraction_screen = g.newPixmap("screens/fraction_screen.png", format);
        history_screen = g.newPixmap("screens/history_screen.png", format);
        info_screen = g.newPixmap("screens/info_screen.png", format);
        settings_screen = g.newPixmap("screens/settings_screen.png", format);

        fractionOrDecimal[0] = g.newPixmap("buttons/fraction_option.png", format);
        fractionOrDecimal[1] = g.newPixmap("buttons/decimal_option.png", format);

        fractionPrecision[0] = g.newPixmap("buttons/16ths.png", format);
        fractionPrecision[1] = g.newPixmap("buttons/32nds.png", format);
        fractionPrecision[2] = g.newPixmap("buttons/64ths.png", format);

        units[0] = g.newPixmap("buttons/inches_only.png", format);
        units[1] = g.newPixmap("buttons/feet_and_inches.png", format);

        history_screen_buttons[0] = g.newPixmap("buttons/history_save.png", format);
        history_screen_buttons[1] = g.newPixmap("buttons/history_enter.png", format);

        orderOfOperations[0] = g.newPixmap("buttons/order_of_ops_on.png", format);
        orderOfOperations[1] = g.newPixmap("buttons/order_of_ops_off.png", format);

        loadPressedButtons(g);
    }

    private static void loadPressedButtons(Graphics g) {

        final Graphics.PixmapFormat format = Graphics.PixmapFormat.RGB565;

        final String prefix = "buttons/pressed/";
        final String suffix = "_pressed.png";

        final String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
        for(int i = 0; i < numbers.length; i++) {
            pressed_buttons_numbers[i] = g.newPixmap(prefix + numbers[i] + suffix, format);
        }

        final String[] operators = {"plus","minus","divide","times"};
        for(int i = 0; i < operators.length; i++) {
            pressed_buttons_operators[i] = g.newPixmap(prefix + operators[i] + suffix, format);
        }

        final String[] calculate = {"fraction","decimal","equals","clear","backspace","feet","inches"};
        for(int i = 0; i < calculate.length; i++) {
            pressed_buttons_calculate[i] = g.newPixmap(prefix + calculate[i] + suffix, format);
        }

        final String[] special = {"fraction_option","decimal_option","16ths","32nds","64ths",
                "inches_only","feet_and_inches","info"};
        for(int i = 0; i < special.length; i++) {
            pressed_buttons_special[i] = g.newPixmap(prefix + special[i] + suffix, format);
        }

        final String[] fractionScreen = {"fraction_screen_slash","fraction_screen_enter"};
        for(int i = 0; i < fractionScreen.length; i++) {
            pressed_buttons_fractionScreen[i] = g.newPixmap(prefix + fractionScreen[i] + suffix, format);
        }

        final String[] historyScreen = {"history_save","history_enter"};
        for(int i = 0; i < historyScreen.length; i++) {
            pressed_buttons_historyScreen[i] = g.newPixmap(prefix + historyScreen[i] + suffix, format);
        }

    }

}
