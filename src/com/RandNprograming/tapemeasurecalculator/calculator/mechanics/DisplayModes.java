package com.RandNprograming.tapemeasurecalculator.calculator.mechanics;

import com.RandNprograming.tapemeasurecalculator.calculator.utilities.Fraction;

public class DisplayModes {

    //------------------------
    //  Fraction or Decimal
    //------------------------
    public static enum FractionOrDecimal {
        FRACTION_OPTION, DECIMAL_OPTION;

        public FractionOrDecimal next() { return values()[(ordinal() + 1) % values().length]; }
    }

    //----------------------
    //  Fraction Precision
    //----------------------
    public static enum FractionPrecision {
        SIXTEENTH, THIRTY_SECOND, SIXTY_FOURTH;

        public FractionPrecision next() { return values()[(ordinal() + 1) % values().length]; }

        /** Returns a fraction object according to the currently selected fractionPrecision button.
         * @return The corresponding fractionPrecision in the form of a Fraction object. */
        public Fraction getFraction() {

            switch (this) {
                case SIXTEENTH: return new Fraction(1, 16);
                case THIRTY_SECOND: return new Fraction(1, 32);
                case SIXTY_FOURTH: return new Fraction(1, 64);
            }
            return null;
        }
    }

    //---------------------
    //  Display Units
    //---------------------
    public static enum DisplayUnits {
        INCHES_ONLY, FEET_AND_INCHES;

        public DisplayUnits next() { return values()[(ordinal() + 1) % values().length]; }
    }
}
