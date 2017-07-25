package com.twentyfour.games.twentyfour;


import android.util.Log;
import java.util.Stack;
import java.util.Queue;
import java.util.ArrayDeque;

import java.util.ArrayList;

class Expression {

    private String _exp;

    Expression(String exp) {
        _exp = exp;
    }

    double solve() {
// Parse expression and put into ArrayList
        ArrayList<String> numOps = new ArrayList<>();
        String exp = _exp;
        int i = 1;
        while (exp.length() > 1) {
            if (exp.substring(i, i + 1).equals(" ")) {
                numOps.add(exp.substring(0, i));
                exp = exp.substring(i + 1);
                i = 0;
            }
            i++;
        }
        System.out.println(numOps);

        String ops = "/*+-";
        Stack<String> operators = new Stack<>();
        Queue<String> output = new ArrayDeque<>();
        for(String token: numOps) {
            if(isNumeric(token)) {
                output.add(token);
            } else if (ops.indexOf(token) != -1) {
                if(!operators.empty()) {
                    int[] currProps = operatorProperties(token);
                    int[] prevProps = operatorProperties(operators.peek());
                    boolean greaterPrec = prevProps[1] > currProps[1];
                    System.out.println("ops: " + operators);
                    System.out.println("out: " + output);
                    while(!operators.empty() && (greaterPrec || (prevProps[1] == currProps[1] && prevProps[2] == 1))) {
                        if(token == "/" && operators.peek() == "+")
                            System.out.println("test");
                        output.add(operators.pop());
                    }
                }
                operators.push(token);
            } else if(token.equals("(")) {
                System.out.println(operators);
                operators.push(token);
            } else {
                System.out.println(operators);
                while(!operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                operators.pop();
            }
        }
        while(!operators.empty()) {
            output.add(operators.pop());
        }
        System.out.println(output);

        Stack<Double> nums = new Stack<>();
        while(!output.isEmpty()) {
            String el = output.peek();
            if(isNumeric(output.peek())) {
                nums.push(Double.parseDouble(output.remove()));
            } else {
                nums.push(operate(nums.pop(), nums.pop(), output.remove()));
            }
        }

        return nums.pop();

        /*
        // Solve using the ArrayList
        double total = 0;
        String operator = "+";
        for (String input : numOps) {
            if (isNumeric(input)) {
                int num = Integer.parseInt(input.trim());
                switch (operator) {
                    case "+":
                        total += num;
                        break;
                    case "-":
                        total -= num;
                        break;
                    case "*":
                        total *= num;
                        break;
                    case "/":
                        total /= num;
                        break;
                }
            } else {
                operator = input.trim();
            }
        }
        */
    }

    static double operate(double a, double b, String op) {
        if(op.equals("+")) {
            return b + a;
        }
        if(op.equals("-")) {
            return b - a;
        }
        if(op.equals("/")) {
            return b / a;
        }
        return b * a;
    }

    /**
     * Returns the properties of the basic mathematical operators
     *
     * @param operator an arithemetic operator or parentheses
     * @return A three element int array. The first element is whether
     * the operator is arithmetic, the second is its precedence from 0-1
     * (-1 if not applicable), and the third is whether it is left
     * associative (-1 if not applicable)
     */
    static int[] operatorProperties(String operator) {
        int[] properties = new int[3];
        switch (operator) {
            case "+":
                properties[0] = 1;
                properties[1] = 0;
                properties[2] = 1;
                break;
            case "-":
                properties[0] = 1;
                properties[1] = 0;
                properties[2] = 0;
                break;
            case "*":
                properties[0] = 1;
                properties[1] = 1;
                properties[2] = 1;
                break;
            case "/":
                properties[0] = 1;
                properties[1] = 1;
                properties[2] = 0;
                break;
            case "(": case ")":
                properties[0] = 0;
                properties[1] = -1;
                properties[2] = -1;
                break;
        }
        return properties;
    }

    /**
     * Determines if input String is a number.
     *
     * @param str any String
     * @return true if str is a number, false otherwise
     */
    static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
