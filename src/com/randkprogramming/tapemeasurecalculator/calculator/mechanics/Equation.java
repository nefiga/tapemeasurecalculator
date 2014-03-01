package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

import java.util.ArrayList;
import java.util.List;

//----------------------------------
// Equation Class
//----------------------------------
/** This class holds an equation with methods for getting and setting things. */
public class Equation {

    //----------------------------------
    // Fields
    //----------------------------------
    public List<String> numbers = new ArrayList<String>();
    public List<Button.Operator> operators = new ArrayList<Button.Operator>();
    public Double result = null;
    public String equation = "";

    //----------------------------------
    // Constructors
    //----------------------------------
    public Equation() {
        clear();
    }
    public Equation(List<String> numbs, List<Button.Operator> opers, Double answer) {
        this.numbers = numbs;
        this.operators = opers;
        this.result = answer;
        updateEquation();
    }

    //----------------------------------
    // Get String
    //----------------------------------
    public String getString() {
        return this.equation;
    }

    //----------------------------------
    // Set Result
    //----------------------------------
    public void setResult(double answer) {
        result = answer;
        updateEquation();
    }

    //----------------------------------
    // Clear
    //----------------------------------
    public void clear() {
        this.numbers.clear();
        this.operators.clear();
        this.result = null;
        this.numbers.add("");
        this.equation = "";
    }

    //----------------------------------
    // Get Last Number
    //----------------------------------
    public String getLastNumber() {
        if(numbers.size() > 0) {
            return numbers.get(numbers.size()-1);
        }
        return "";
    }

    //----------------------------------
    // Set Last Number
    //----------------------------------
    public void setLastNumber(String num) {

        if(numbers.size() > 0) {
            numbers.set(numbers.size() - 1, num);
        }
    }

    //----------------------------------
    // Append to Last Number
    //----------------------------------
    public void appendToLastNum(String num) {

        setLastNumber(getLastNumber() + num);
    }

    //----------------------------------
    // Get Last Operator
    //----------------------------------
    public Button.Operator getLastOperator() {
        if(operators.size() > 0) {
            return operators.get(operators.size()-1);
        }
        return null;
    }

    //----------------------------------
    // Convert Units to Symbols
    //----------------------------------
    public void convertUnitsToSymbols() {

        // Replace words with corresponding symbols
        setLastNumber(getLastNumber().replaceAll(" Feet", "\'"));
        setLastNumber(getLastNumber().replaceAll(" Inches", "\""));
    }

    //----------------------------------
    // Most Recent Operator
    //----------------------------------
    /** Checks to see whether the last operator entered in was a times or a divide.
     * @return True if the latest one is a times or a divide. False otherwise. */
    public boolean mostRecentOperatorIsTimesOrDivide() {

        boolean result = false;
        if(operators.size() > 0) {
            Button.Operator mostRecentOp = operators.get(operators.size()-1);
            result = mostRecentOp.equals(Button.Operator.TIMES) || mostRecentOp.equals(Button.Operator.DIVIDE);
        }
        return result;
    }

    //----------------------------------
    // Is Operator Next
    //----------------------------------
    /** Checks to see if a number was the last thing entered.
     * @return (boolean) Returns true if a number was the last thing entered. */
    public boolean isOperatorNext() {

        String s = getLastNumber();

        if(s == null || s.length() == 0) {
            return false;
        }
        return operators.size() < numbers.size();
    }

    //----------------------------------
    // Update Equation
    //----------------------------------
    /** Builds the string of the equation from the numbers and operators currently stored. */
    public void updateEquation() {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < numbers.size(); i++) {

            sb.append(numbers.get(i));

            if(i < operators.size()) {
                sb.append(operators.get(i).toString());
            }
        }
        equation = sb.toString();
    }

    //----------------------------------
    // Update Display
    //----------------------------------
    public void updateDisplay() {

        if(numbers.size() == 1 && numbers.get(0).length() > 0) {

            if(result == null) {
                result = ParserConverter.parseNumber(numbers.get(0));
            }
            numbers.set(0, ParserConverter.formatToString(result));
            updateEquation();
        }
    }

    //----------------------------------
    //  Verify Units
    //----------------------------------
    /** If user forgot to specify feet or inches, insert inches by default. */
    public void verifyUnits() {

        String num = getLastNumber();

        // Don't allow units until they have at least one number
        if(numbers.size() == 0 || num.length() == 0)
            return;

        if( ! num.contains("\"") && Character.isDigit(num.charAt(num.length()-1)) ) {

            // Ignore adding the unit symbols for multiplications or divisions
            if(mostRecentOperatorIsTimesOrDivide()) {
                return;
            }
            appendToLastNum("\"");
        }
    }

    //----------------------------------
    //  Copy
    //----------------------------------
    public Equation copy() {

        List<String> cloneNums = new ArrayList<String>(numbers.size());
        for(String s : numbers) { cloneNums.add(s); }

        List<Button.Operator> cloneOps = new ArrayList<Button.Operator>(operators.size());
        for(Button.Operator op : operators) { cloneOps.add(op); }

        Double cloneResult = null;
        if(result != null) {
            cloneResult = Double.valueOf(result);
        }

        return new Equation(cloneNums,cloneOps,cloneResult);
    }


}