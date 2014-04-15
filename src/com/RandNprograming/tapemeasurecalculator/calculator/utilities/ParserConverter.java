package com.RandNprograming.tapemeasurecalculator.calculator.utilities;

import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.DisplayModes;

import java.text.DecimalFormat;

/** Has two methods for converting a number between a string and a number. */
public class ParserConverter {

    /**
     * Converts a number into a string of text based on the currently selected display mode.
     * (Inches only, Feet + Inches, or Decimal)
     * @param number The number that needs to be converted
     * @return The formatted result
     */
    public static String formatToString(double number, int unitDimension) {

        DisplayModes.FractionOrDecimal fractionOrDecimal = CalcState.fractionOrDecimal;
        DisplayModes.DisplayUnits units = CalcState.displayUnits;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(6);

        String text = "";

        if(number < 0) {
            text += "-";
            number *= -1;
        }

        if(unitDimension > 1) {
            if(units == DisplayModes.DisplayUnits.FEET_AND_INCHES) {

                double feet = convertToFeetWithDimensionality(number, unitDimension);
                String n = df.format(feet);
                text += n;
                text += " ft";
            }
            else if(units == DisplayModes.DisplayUnits.INCHES_ONLY) {

                String n = df.format(number);
                text += n;
                text += " in";
            }
            text += unitDimension;
        }
        else if(fractionOrDecimal == DisplayModes.FractionOrDecimal.DECIMAL_OPTION) {

            if(units == DisplayModes.DisplayUnits.FEET_AND_INCHES) {
                number /= 12;
            }
            String n = df.format(number);
            text += n;
            text += (units == DisplayModes.DisplayUnits.FEET_AND_INCHES) ? " Feet" : " Inches";
        }
        else {

            if(units == DisplayModes.DisplayUnits.FEET_AND_INCHES) {

                // Parse Feet
                int feet = (int) number / 12;
                number -= feet * 12;
                text += feet;
                text += "\' ";
            }

            // Parse Inches
            int inches = (int) number;
            number -= inches;

            // Parse Decimal
            boolean fractionValid = false;
            String fraction = Fraction.getFractionStringFromDecimal(number, CalcState.fractionPrecision.getFraction());
            if(fraction.length() > 0 && fraction.charAt(0) != '0') {

                if(fraction.equals("1/1")) {
                    inches++;
                }
                else {
                    fractionValid = true;
                }
            }
            text += inches;
            if(fractionValid) {
                text += "  ";
                text += fraction;
            }
            text += "\"";
        }

        return text;
    }

    /**
     * Converts a number from inches into feet according to the right dimensions. For example: 144 in^2
     * would result in 1 ft^2. It divides the number by 12, x amount of times, where x is the dimension.
     * @param number The number that needs to be converted into feet.
     * @param dimension The dimensionality of the number. ft^3 is 3. in^2 is 2. just inches is 1, etc..
     * @return The converted answer in feet.
     */
    private static double convertToFeetWithDimensionality(double number, int dimension) {

        while (dimension > 0) {
            number /= 12;
            dimension--;
        }
        return number;
    }

    /** Converts a number from a string into a decimal number (result is in inches as a decimal).
     * This will take into account decimals, fractions, feet, and inches.
     * Format depends on fractionPrecision mode and display option. Examples are:
     * 4' 7 8/9"  4.353 Feet  3 7/8"  5.26 Inches   All of these are valid numbers.
     * Some assumptions this parser makes are:
     * Ft. symbol (') will always be there when describing feet.
     * No space between the feet symbol and the number on the left of it.
     * @param s The string that needs to be converted
     * @return The converted result */
    public static double parseNumber(String s) {

        double value = 0;
        String read = "";
        Fraction f = new Fraction();
        boolean readingFraction = false;
        boolean isNegative = false;

        for(int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if(Character.isSpaceChar(c)) {

                if(read.length() > 0) {

                    // If the next letter is an F, then assume it is going to say Feet
                    if(s.length() > i+1 && s.charAt(i+1) == 'F') {
                        value += Double.parseDouble(read) * 12;
                        i += 4; // Skip the word Feet
                    }
                    else if(s.length() > i+1 && s.charAt(i+1) == 'I') {
                        value += Double.parseDouble(read);
                        i += 6; // Skip the word Inches
                    }
                    // If the next letter is lowercase f, then assume it is going to say ft2, or ft3 (with a dimension)
                    else if(s.length() > i+1 && s.charAt(i+1) == 'f') {

                        value += Double.parseDouble(read);
                        i += 2; // Skip the word ft

                        // Find the dimension
                        String dim = "";
                        while(s.length() > i && ! Character.isSpaceChar(s.charAt(i))) {
                            dim += s.charAt(i);
                            i++;
                        }
                        int dimension = Integer.parseInt(dim);

                        // Multiply by 12 for every dimension
                        while(dimension > 0) {
                            value += 12;
                            dimension--;
                        }
                    }
                    else if(s.length() > i+1 && s.charAt(i+1) == 'i') {

                        value += Double.parseDouble(read);
                        i += 2; // Skip the word in

                        // Skip the dimension number
                        while(s.length() > i && !Character.isSpaceChar(s.charAt(i))) {
                            i++;
                        }
                    }
                    else {
                        value += Double.parseDouble(read);
                    }
                    read = "";
                }
                continue;
            }

            switch (c) {
                case '-': {
                    isNegative = true;
                    break;
                }
                case '\'': {
                    value += Double.parseDouble(read) * 12;
                    read = "";
                    break;
                }
                case '\"': {
                    if(readingFraction) {
                        f = new Fraction(f.getNumerator(), Integer.parseInt(read));
                        value += f.getDecimalRepresentation();
                        read = "";
                    }
                    else {
                        value += Double.parseDouble(read);
                        read = "";
                    }
                    break;
                }
                case '/': {
                    readingFraction = true;
                    f = new Fraction(Integer.parseInt(read), 1); // Denominator will get set later
                    read = "";
                    break;
                }
                default: {
                    read += c;
                    break;
                }
            }
        }

        if(read.length() > 0) {
            value += Double.parseDouble(read);
            read = "";
        }

        if(isNegative) {
            value *= -1;
        }

        return value;
    }
}
