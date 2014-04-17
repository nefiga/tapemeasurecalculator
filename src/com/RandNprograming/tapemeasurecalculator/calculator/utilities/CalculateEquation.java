package com.RandNprograming.tapemeasurecalculator.calculator.utilities;

import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalcHistory;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.CalcState;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.Equation;
import com.RandNprograming.tapemeasurecalculator.calculator.mechanics.Operator;

public class CalculateEquation {

    public static class DimensionalErrorException extends Exception{};

    /** Figures the order of operation. Then solves the equation. The equation must end with numbers.
     * This method will do nothing if the equation ends with an operator. */
    public static void solveEquation(Equation equation, boolean orderOfOps) {

        if ( equation.isOperatorNext()) {

            equation.convertUnitsToSymbols();
            equation.verifyUnits();

            Equation historic = equation.copy();

            try {

                if(orderOfOps) {
                    calculate(equation,true,true);
                    calculate(equation,true,false);
                }
                else {
                    calculate(equation,false,true);
                }

                String answer = equation.getNumbers().get(0);
                equation.clear();
                double answerDouble = ParserConverter.parseNumber(answer);
                equation.setUnitDimension(countUnits(answer));

                equation.getNumbers().set(0, ParserConverter.formatToString(answerDouble, equation.getUnitDimension()));
                equation.setResult(answerDouble);
                historic.setResult(answerDouble);
                historic.setUnitDimension(equation.getUnitDimension());
                CalcHistory.add(historic);
            }
            catch (DimensionalErrorException e) {
                CalcState.equation = historic;
                System.out.println("DIMENSIONAL ERROR!");
            }
        }
    }

    /** Runs through numbers and operators and performs multiplication and division and shifts
     * them to the left each time an operation happens. */
    private static void calculate(Equation equation, boolean orderOfOps, boolean multAndDivide) throws DimensionalErrorException {

        for (int i = 0; i < equation.getOperators().size(); i++) {

            Operator operator = equation.getOperators().get(i);

            String str1 = equation.getNumbers().get(i);
            String str2 = equation.getNumbers().get(i + 1);

            double first = ParserConverter.parseNumber(str1);
            double second = ParserConverter.parseNumber(str2);

            double answer = 0;
            int unitCount1 = countUnits(str1); // Number of units that the first number has (ft^2 would be 2, in^3 would be 3, etc..)
            int unitCount2 = countUnits(str2);

            if(operator == Operator.TIMES || operator == Operator.DIVIDE) {

                if(orderOfOps && !multAndDivide) {
                    continue;
                }

                if(operator == Operator.TIMES) {
                    answer = first * second;
                }
                else if(operator == Operator.DIVIDE) {
                    answer = first / second;
                }
            }
            else if(operator == Operator.PLUS || operator == Operator.MINUS) {

                if(orderOfOps && multAndDivide) {
                    continue;
                }

                if(unitCount1 != unitCount2) {
                    throw new DimensionalErrorException();
                }

                if(operator == Operator.PLUS) {
                    answer = first + second;
                }
                else if(operator == Operator.MINUS) {
                    answer = first - second;
                }
            }
            else {
                continue;
            }

            String answerString = formatAnswerWithUnits(answer,operator,unitCount1,unitCount2);

            equation.getNumbers().set(i, answerString);
            equation.getNumbers().remove(i + 1);
            equation.getOperators().remove(i);
            i--;
        }
    }

    public static String formatAnswerWithUnits(double unformattedAnswer, Operator operator, int unitCount1, int unitCount2) {

        String formattedAnswer = "" + unformattedAnswer;

        if(operator == Operator.TIMES) {

            if(unitCount1 + unitCount2 == 1) {
                formattedAnswer += "\"";
            }
            else if (unitCount1 + unitCount2 > 1) {
                formattedAnswer += " in" + (unitCount1 + unitCount2);
            }

        }
        else if(operator == Operator.DIVIDE) {

            if(unitCount1 - unitCount2 == 1) {
                formattedAnswer += "\"";
            }
            else if(unitCount1 - unitCount2 != 0) {
                formattedAnswer += " in" + (unitCount1 - unitCount2);
            }
        }
        else if(operator == Operator.PLUS || operator == Operator.MINUS) {

            if(unitCount1 == 1) {
                formattedAnswer += "\"";
            }
            else {
                formattedAnswer += " in" + unitCount1;
            }
        }

        return formattedAnswer;
    }

    private static int countUnits(String str) {

        int result = 0;

        if(str.contains("ft")) {
            int pos = str.indexOf("ft");
            result += Integer.parseInt(str.substring(pos + 2));
        }
        else if(str.contains("in")) {
            int pos = str.indexOf("in");
            result += Integer.parseInt(str.substring(pos + 2));
        }
        else if(str.contains("\'") || str.contains("\"")) {
            result++;
        }

        return result;

    }
}
