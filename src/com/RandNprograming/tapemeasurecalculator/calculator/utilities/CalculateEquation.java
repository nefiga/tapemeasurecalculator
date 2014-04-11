package com.RandNprograming.tapemeasurecalculator.calculator.utilities;

import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalcHistory;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.DisplayModes;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.Equation;

public class CalculateEquation {

    /** Figures the order of operation. Then solves the equation. The equation must end with numbers.
     * This method will do nothing if the equation ends with an operator. */
    public static void solveEquation(Equation equation, boolean orderOfOps) {

        if ( equation.isOperatorNext()) {

            equation.convertUnitsToSymbols();
            equation.verifyUnits();

            Equation historic = equation.copy();

            if(orderOfOps) {
                calculateWithOrder(equation, true);
                calculateWithOrder(equation, false);
            }
            else {
                calculateWithoutOrder(equation);
            }

            String answer = equation.getNumbers().get(0);
            equation.clear();
            double answerDouble = ParserConverter.parseNumber(answer);

            equation.getNumbers().set(0, ParserConverter.formatToString(answerDouble));
            equation.setResult(answerDouble);
            historic.setResult(answerDouble);
            CalcHistory.add(historic);
        }
    }

    /** Runs through numbers and operators and performs multiplication and division and shifts
     * them to the left each time an operation happens. */
     private static void calculateWithOrder(Equation equation, boolean multAndDivide)    {

         for (int i = 0; i < equation.getOperators().size(); i++) {

             double first = ParserConverter.parseNumber(equation.getNumbers().get(i));
             double second = ParserConverter.parseNumber(equation.getNumbers().get(i + 1));

             switch(equation.getOperators().get(i)) {

                 case  TIMES: { if(!multAndDivide) continue;
                     if (CalcState.displayUnits == DisplayModes.DisplayUnits.FEET_AND_INCHES) equation.getNumbers().set(i, "" + (first * second / 12) + "\"");
                     else equation.getNumbers().set(i, "" + (first * second) + "\"");
                      break; }
                 case DIVIDE: { if(!multAndDivide) continue; equation.getNumbers().set(i, "" + (first / second) + "\""); break; }
                 case  PLUS: { if(multAndDivide) continue; equation.getNumbers().set(i, "" + (first + second) + "\"" ); break; }
                 case MINUS: { if(multAndDivide) continue; equation.getNumbers().set(i, "" + (first - second) + "\"" ); break; }
                 default: { continue; }
             }

             equation.getNumbers().remove(i + 1);
             equation.getOperators().remove(i);
             i--;
         }
     }
     private static void calculateWithoutOrder(Equation equation) {

        for (int i = 0; i < equation.getOperators().size(); i++) {

            double first = ParserConverter.parseNumber(equation.getNumbers().get(i));
            double second = ParserConverter.parseNumber(equation.getNumbers().get(i + 1));

            switch(equation.getOperators().get(i)) {

                case  TIMES: { equation.getNumbers().set(i, "" + (first * second) + "\""); break; }
                case DIVIDE: { equation.getNumbers().set(i, "" + (first / second) + "\""); break; }
                case  PLUS: { equation.getNumbers().set(i, "" + (first + second) + "\"" ); break; }
                case MINUS: { equation.getNumbers().set(i, "" + (first - second) + "\"" ); break; }
                default: { continue; }
            }

            equation.getNumbers().remove(i + 1);
            equation.getOperators().remove(i);
            i--;
        }
    }

}