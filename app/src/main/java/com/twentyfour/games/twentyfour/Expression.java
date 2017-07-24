package com.twentyfour.games.twentyfour;


import android.util.Log;
import java.util.Stack;

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

        String ops = "()+-/*%sqrt";
        Stack<String> operators = new Stack<String>();
        Stack<Double> nums = new Stack<Double>();
        for(String token: numOps) {
            if(token.equals("("));
            else if(token.equals(")")) {
                String op = operators.pop();
                if(op.equals("sqrt")) {
                    nums.push(Math.sqrt(nums.pop()));
                } else {
                    nums.push(operate(nums.pop(),nums.pop(),op));
                }
            } else if(ops.indexOf(token) != -1) {
                operators.push(token);
            } else {
                nums.push(Double.parseDouble(token));
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
