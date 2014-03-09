package com.randkprogramming.tapemeasurecalculator.calculator.buttons;

import java.util.ArrayList;
import java.util.List;

public class ButtonLayout {

    /** Contains a list of buttons. The order shouldn't matter.*/
    public static List<Button> mainScreenButtons = new ArrayList<Button>();
    public static List<Button> fractionScreenButtons = new ArrayList<Button>();
    public static List<Button> historyScreenButtons = new ArrayList<Button>();

    public static void setupButtons() {

        setupMainScreenButtons();
        setupFractionScreenButtons();
        setupHistoryScreenButtons();
    }

    private static void setupMainScreenButtons() {

        final int[] x = { 68, 251, 434, 617 };
        final int[] y = { 330, 475, 620, 765, 910, 1135 };
        final ButtonAction action[][] = new ButtonAction[][]{
                new ButtonAction[]{ButtonAction.CalculateAction.CLEAR, ButtonAction.OperatorAction.DIVIDE, ButtonAction.OperatorAction.TIMES, ButtonAction.CalculateAction.BACKSPACE},
                new ButtonAction[]{ButtonAction.NumberAction.SEVEN, ButtonAction.NumberAction.EIGHT, ButtonAction.NumberAction.NINE, ButtonAction.OperatorAction.MINUS},
                new ButtonAction[]{ButtonAction.NumberAction.FOUR, ButtonAction.NumberAction.FIVE, ButtonAction.NumberAction.SIX, ButtonAction.OperatorAction.PLUS},
                new ButtonAction[]{ButtonAction.NumberAction.ONE, ButtonAction.NumberAction.TWO, ButtonAction.NumberAction.THREE, ButtonAction.CalculateAction.FEET},
                new ButtonAction[]{ButtonAction.CalculateAction.DECIMAL_POINT, ButtonAction.NumberAction.ZERO, ButtonAction.CalculateAction.FRACTION, ButtonAction.CalculateAction.EQUALS},
                new ButtonAction[]{ButtonAction.SpecialAction.FRACTION_OR_DECIMAL, ButtonAction.SpecialAction.FRACTION_PRECISION, ButtonAction.SpecialAction.DISPLAY_UNITS, ButtonAction.SpecialAction.INFO},
        };

        for(int row = 0; row < y.length; row++) {
            for(int col = 0; col < x.length; col++) {
                mainScreenButtons.add(new Button(x[col],y[row],action[row][col]));
            }
        }
    }

    private static void setupFractionScreenButtons() {

        final int[] x = { 68, 251, 434, 617 };
        final int[] y = { 330, 475, 620, 765, 910 };
        final ButtonAction action[][] = new ButtonAction[][]{
                new ButtonAction[]{ButtonAction.ManualFractionAction.CLEAR, null, null, ButtonAction.ManualFractionAction.BACK},
                new ButtonAction[]{ButtonAction.ManualFractionAction.SEVEN, ButtonAction.ManualFractionAction.EIGHT, ButtonAction.ManualFractionAction.NINE, null},
                new ButtonAction[]{ButtonAction.ManualFractionAction.FOUR, ButtonAction.ManualFractionAction.FIVE, ButtonAction.ManualFractionAction.SIX, null},
                new ButtonAction[]{ButtonAction.ManualFractionAction.ONE, ButtonAction.ManualFractionAction.TWO, ButtonAction.ManualFractionAction.THREE, null},
                new ButtonAction[]{null, ButtonAction.ManualFractionAction.ZERO, null, null},
        };

        for(int row = 0; row < y.length; row++) {
            for(int col = 0; col < x.length; col++) {
                if(action[row][col] != null)
                    fractionScreenButtons.add(new Button(x[col],y[row],action[row][col]));
            }
        }

        fractionScreenButtons.add(new Button(68,1055,663,140,34,1040,731,170,ButtonAction.ManualFractionAction.ENTER));
        fractionScreenButtons.add(new Button(617,475,115,405,583,460,183,435,ButtonAction.ManualFractionAction.FRACTION));
    }

    private static void setupHistoryScreenButtons() {

        historyScreenButtons.add(new Button(90,102,280,80,60,85,340,114,ButtonAction.HistoryAction.SAVE));
//        historyScreenButtons.add(new Button(430,102,280,80,400,85,340,114,ButtonAction.HistoryAction.ENTER));
        historyScreenButtons.add(new Button(260,102,280,80,230,85,340,114,ButtonAction.HistoryAction.ENTER));
    }



}
