package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

public class CalculateEquation {

    ButtonActions buttonActions;

    private double[] numbers = new double[8];
    private int[] operators = new int[7];
    private int operatorPosition = 0, numberPosition = 0;
    private String number = "";
    private String total = "";
    private String equation = "";

    public CalculateEquation(ButtonActions buttonActions) {
        this.buttonActions = buttonActions;
        resetNumbers(0);
        resetOperators(0);
    }

    /**
     * Decides what action to take depending on what button was pressed
     *
     * @param button      (int) Button that was pressed
     * @param buttonState (int) Whether the button was pressed or held
     */
    public void sortButton(int button, int buttonState) {
        if (button > CalculatorButtons.NONE && button < CalculatorButtons.NUMBER_BUTTONS && buttonState == CalculatorButtons.BUTTON_PRESSED) {
            addNumber(button);
        } else if (button < CalculatorButtons.OPERATOR_BUTTONS && buttonState == CalculatorButtons.BUTTON_PRESSED) {
            addOperator(button);
        } else if (button == CalculatorButtons.DECIMAL_POINT && buttonState == CalculatorButtons.BUTTON_PRESSED) {
            addDecimal();
        } else if (button == CalculatorButtons.CLEAR) {
            if (buttonState == CalculatorButtons.BUTTON_PRESSED) {
                pressedClear();
            } else if (buttonState == CalculatorButtons.BUTTON_HELD) {
                holdClear();
            }
        } else if (button == CalculatorButtons.EQUALS && buttonState == CalculatorButtons.BUTTON_PRESSED) {
            solveEquation(buttonActions.getCurrentFractionButton(), true);
        }
    }

    /**
     * Adds the operator corresponding to the button that was pressed.
     *
     * @param button (int) Button that was pressed.
     */
    public void addOperator(int button) {
        if (isOperatorNext() && operatorPosition < operators.length) {
            //Takes all the numbers that have been add put them in the corresponding numbers[] position.
            //Resets the number(String), adds 1 to numberPosition
            number = "";
            numberPosition++;
            //If this is the second operator or greater, increase operatorPosition.
            if (operators[0] != CalculatorButtons.NONE) operatorPosition++;
            //Adds the operator to the equation.
            operators[operatorPosition] = button;
            if (button == CalculatorButtons.PLUS) {
                equation += " + ";
            } else if (button == CalculatorButtons.MINUS) {
                equation += " - ";
            } else if (button == CalculatorButtons.DIVIDE) {
                equation += " ÷ ";
            } else if (button == CalculatorButtons.TIMES) {
                equation += " x ";
            }
        }
    }

    /**
     * Adds the number corresponding the the button that was pressed.
     *
     * @param button (int) Button that was pressed.
     */
    public void addNumber(int button) {
        number += button;
        numbers[numberPosition] = Double.valueOf(number);
        equation += button;
    }

    /**
     * Adds a decimal to the equation
     */
    public void addDecimal() {
        if (!number.contains(".")) {
            number += ".";
            equation += ".";
            if (!number.equals(".")) {
                numbers[numberPosition] = Double.valueOf(number);
            }
        }
    }

    /**
     * Sets numbers[] int, at position, to default. Also subtracts one form numberPosition.
     */
    public void decreesNumberPosition() {
        numbers[numberPosition] = CalculatorButtons.NONE;
        if (numberPosition > 0) {
            numberPosition--;
        }
    }

    /**
     * Sets operators[] int, at position, to default. Also subtracts one from operatorPosition.
     */
    public void decreesOperatorPosition() {
        if (operatorPosition > 0) {
            operators[operatorPosition] = CalculatorButtons.NONE;
            operatorPosition--;
        } else {
            operators[operatorPosition] = CalculatorButtons.NONE;
        }
    }

    /**
     * Sets all numbers[] starting at "start" to default. Also sets numberPosition to "start" - 1, unless start is zero then numberPosition = 0.
     *
     * @param start (int) What position to start resetting numbers[] at.
     */
    public void resetNumbers(int start) {
        for (int i = start; i < numbers.length; i++) {
            numbers[i] = CalculatorButtons.NONE;
        }
        if (start > 0) numberPosition = start - 1;
        else numberPosition = 0;
    }

    /**
     * Sets all operators[] starting at "start" to default. Also sets operatorPosition to "start" - 1, unless start is zero then operatorPosition = 0.
     *
     * @param start (int) What position to start resetting operators[] at.
     */
    public void resetOperators(int start) {
        for (int i = start; i < operators.length; i++) {
            operators[i] = CalculatorButtons.NONE;
        }
        if (start > 0) operatorPosition = start - 1;
        else operatorPosition = 0;
    }

    /**
     * Removes the last operator/number in the equation.
     */
    public void pressedClear() {
        if (isOperatorNext()) {
            numbers[numberPosition] = CalculatorButtons.NONE;
            number = "";
        } else {
            decreesOperatorPosition();
        }
        updateEquation();
    }

    /**
     * Resets everything in the equation.
     */
    public void holdClear() {
        resetNumbers(0);
        resetOperators(0);
        number = "";
        total = "";
        equation = "";
    }

    private void updateEquation() {
        equation = "";

        for (int i = 0; i <= numberPosition; i++) {

            if (numbers[i] != CalculatorButtons.NONE) equation += Double.toString(numbers[i]);

            //Offsets operator by one because operators[] will always be one less than numbers[]
            if (i <= operatorPosition) {
                if (operators[i] == CalculatorButtons.PLUS) {
                    equation += " + ";
                } else if (operators[i] == CalculatorButtons.MINUS) {
                    equation += " - ";
                } else if (operators[i] == CalculatorButtons.DIVIDE) {
                    equation += " ÷ ";
                } else if (operators[i] == CalculatorButtons.TIMES) {
                    equation += " x ";
                }
            }
        }
    }

    /**
     * Checks to see if a number was the last thing entered.
     *
     * @return (boolean) Returns true if a number was the last thing entered.
     */
    public boolean isOperatorNext() {
        int operatorCount = 0, numberCount = 0;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > CalculatorButtons.NONE) {
                numberCount++;
            }
        }
        for (int i = 0; i < operators.length; i++) {
            if (operators[i] > CalculatorButtons.NONE) {
                operatorCount++;
            }
        }

        if (operatorCount < numberCount)
            return true;
        else
            return false;
    }

    /**
     * Returns the equations last total.
     *
     * @return (String)
     */
    public String getTotal() {
        return total;
    }

    /**
     * Returns the equations.
     *
     * @return (String)
     */
    public String getEquation() {
        return equation;
    }

    /**
     * Figures the order of operation. Then solves the equation. The equation must end with numbers.
     * This method will do nothing if the equation ends with an operator.
     */
    public void solveEquation(int fractionButton, boolean inches) {
        int counter = 0;
        if (isOperatorNext() && numberPosition >= 1) {
            //Solve Times and Divide fist
            for (int i = 0; i <= operatorPosition; i++) {
                if (operators[i] == CalculatorButtons.DIVIDE || operators[i] == CalculatorButtons.TIMES) {
                    if (operators[i] == CalculatorButtons.DIVIDE) {
                        numbers[i + 1] = numbers[i] / numbers[i + 1];
                    } else if (operators[i] == CalculatorButtons.TIMES) {
                        numbers[i + 1] = numbers[i] * numbers[i + 1];
                    }
                    for (int j = i; j > 0; j--) {
                        operators[j] = operators[j - 1];
                        numbers[j] = numbers[j - 1];
                    }
                    counter++;
                }
            }

            for (int i = counter; i <= operatorPosition; i++) {
                if (operators[i] == CalculatorButtons.PLUS) {
                    numbers[i + 1] = numbers[i] + numbers[i + 1];
                } else if (operators[i] == CalculatorButtons.MINUS) {
                    numbers[i + 1] = numbers[i] - numbers[i + 1];
                }
            }

            numbers[0] = numbers[numberPosition];
            resetNumbers(1);
            resetOperators(0);
            number = Double.toString(numbers[0]);
            if (fractionButton == CalculatorButtons.SIXTEENTH) {
                equation = Double.toString(numbers[0]);
            } else if (fractionButton == CalculatorButtons.THIRTYSECOND) {

            } else if (fractionButton == CalculatorButtons.SIXTYFOURTH) {

            } else if (fractionButton == CalculatorButtons.DECIMAL) {

            } else {

            }
        }
    }
}
