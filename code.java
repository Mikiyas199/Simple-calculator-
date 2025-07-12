package com.houarizegai.calculator.logic;

public class CalculationEngine {

    private String displayValue = "0";
    private Double firstOperand = null;
    private Character operator = null;
    private boolean clearDisplayOnNextDigit = true;

    public String getDisplayValue() {
        return displayValue;
    }

    public void appendDigit(String digit) {
        if (clearDisplayOnNextDigit) {
            displayValue = digit;
            clearDisplayOnNextDigit = false;
        } else {
            if (displayValue.equals("0")) {
                displayValue = digit;
            } else {
                displayValue += digit;
            }
        }
    }

    public void appendPoint() {
        if (clearDisplayOnNextDigit) {
            displayValue = "0.";
            clearDisplayOnNextDigit = false;
        } else if (!displayValue.contains(".")) {
            displayValue += ".";
        }
    }

    public void setOperator(char op) {
        if (operator != null && clearDisplayOnNextDigit) {
            operator = op;
            return;
        }
        if (firstOperand != null && operator != null && !clearDisplayOnNextDigit) {
            calculate();
        }

        firstOperand = Double.parseDouble(displayValue);
        operator = op;
        clearDisplayOnNextDigit = true;
    }

    public void calculate() {
        if (firstOperand == null || operator == null) {
            return;
        }

        double secondOperand = Double.parseDouble(displayValue);
        double result;

        switch (operator) {
            case '+': result = firstOperand + secondOperand; break;
            case '-': result = firstOperand - secondOperand; break;
            case '*': result = firstOperand * secondOperand; break;
            case '/':
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    displayValue = "Error";
                    reset();
                    return;
                }
                break;
            case '%': result = firstOperand % secondOperand; break;
            case '^': result = Math.pow(firstOperand, secondOperand); break;
            default: return; // Should not happen
        }

        displayValue = formatResult(result);
        firstOperand = result;
        operator = null;
        clearDisplayOnNextDigit = true;
    }

    public void performUnaryOperation(String op) {
        if (displayValue.equals("Error")) return;

        double currentValue = Double.parseDouble(displayValue);
        double result;

        switch (op) {
            case "√": result = Math.sqrt(currentValue); break;
            case "ln": result = Math.log(currentValue); break;
            default: return;
        }

        displayValue = formatResult(result);
        clearDisplayOnNextDigit = true;
    }

    public void clear() {
        displayValue = "0";
        reset();
    }

    private void reset() {
        firstOperand = null;
        operator = null;
        clearDisplayOnNextDigit = true;
    }

    public void backspace() {
        if (clearDisplayOnNextDigit) return;

        if (displayValue.length() > 1) {
            displayValue = displayValue.substring(0, displayValue.length() - 1);
        } else {
            displayValue = "0";
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.valueOf(result);
        }
    }
}