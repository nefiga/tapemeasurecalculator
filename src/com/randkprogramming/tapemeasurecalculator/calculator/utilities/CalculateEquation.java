package com.randkprogramming.tapemeasurecalculator.calculator.utilities;

import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalcHistory;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.randkprogramming.tapemeasurecalculator.calculator.mechanics.Equation;

public class CalculateEquation {

    /** Figures the order of operation. Then solves the equation. The equation must end with numbers.
     * This method will do nothing if the equation ends with an operator. */
    public static void solveEquation(Equation equation) {

        if ( equation.isOperatorNext()) {

            equation.convertUnitsToSymbols();
            equation.verifyUnits();

            Equation historic = equation.copy();

            doOperations(equation, true);
            doOperations(equation, false);

            String answer = equation.getNumbers().get(0);
            equation.clear();
            double answerDouble = ParserConverter.parseNumber(answer);
            equation.getNumbers().set(0, ParserConverter.formatToString(answerDouble));
            equation.setResult(answerDouble);
            historic.setResult(answerDouble);
            CalcHistory.add(historic);
            CalcState.paint.update(CalcState.equation.getEquation());
        }
    }

    /** Runs through numbers and operators and performs multiplication and division and shifts
     * them to the left each time an operation happens. */
    private static void doOperations(Equation equation, boolean multAndDivide) {

        for (int i = 0; i < equation.getOperators().size(); i++) {

            double first = ParserConverter.parseNumber(equation.getNumbers().get(i));
            double second = ParserConverter.parseNumber(equation.getNumbers().get(i + 1));

//            switch(equation.getOperators().get(i)) {
//
//                case  TIMES: { if(!multAndDivide) continue; equation.getNumbers().set(i, "" + (first * second) + "\""); break; }
//                case DIVIDE: { if(!multAndDivide) continue; equation.getNumbers().set(i, "" + (first / second) + "\""); break; }
//                case  PLUS: { if(multAndDivide) continue; equation.getNumbers().set(i, "" + (first + second) + "\"" ); break; }
//                case MINUS: { if(multAndDivide) continue; equation.getNumbers().set(i, "" + (first - second) + "\"" ); break; }
//                default: { continue; }
//            }
//
//            equation.getNumbers().remove(i + 1);
//            equation.getOperators().remove(i);
//            i--;
        }
    }

}