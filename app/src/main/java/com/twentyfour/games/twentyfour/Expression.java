package com.twentyfour.games.twentyfour;


import android.util.Log;

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

        return total;
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
