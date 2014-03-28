package com.RandNprograming.tapemeasurecalculator.calculator.mechanics;

public enum Operator {

    PLUS, MINUS, DIVIDE, TIMES;

    @Override
    public String toString() {

        switch (this) {
            case PLUS: { return " + "; }
            case MINUS: { return " - "; }
            case DIVIDE: { return " ÷ "; }
            case TIMES: { return " x "; }
            default: { return "";  }
        }
    }

}
