package com.twentyfour.games.twentyfour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.twentyfour.games.twentyfour.Expression.isNumeric;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Generate numbers
        int eqType = (int) (Math.random() * 1);
        int total = 24;
        int[] nums = new int[4];

        if (eqType == 0) {
            // chain eq
            for (int i = 0; i < 3; i++) {

                int operation = randomOperation();
                switch (operation) {
                    case 0:
                        nums[i] = (int) (Math.random() * (total - 1)) + 1; // 1 - total
                        total -= nums[i];
                        break;
                    case 1:
                        nums[i] = (int) (Math.random() * (total - 1)) + 1;
                        total += nums[i];
                        break;
                    case 2:
                        nums[i] = (int) (Math.random() * (total - 1)) + 1;
                        while (!isDivisible(total, nums[i])) {
                            nums[i] = (int) (Math.random() * (total - 1)) + 1;
                        }
                        total /= nums[i];
                        break;
                    case 3:
                        nums[i] = (int) (Math.random() * (total - 1)) + 1;
                        total *= nums[i];
                        break;
                }
            }
            nums[3] = total;

        } else {
            // (x + y) - (a * b) eq

        }

        final TextView inputTextView = (TextView) findViewById(R.id.input);
        final TextView totalTextView = (TextView) findViewById(R.id.total);

        final Button button1 = (Button) findViewById(R.id.num1);
        final Button button2 = (Button) findViewById(R.id.num2);
        final Button button3 = (Button) findViewById(R.id.num3);
        final Button button4 = (Button) findViewById(R.id.num4);

        View.OnClickListener updateInput = new View.OnClickListener() {
            public void onClick(View view) {
                String inputText = inputTextView.getText().toString();
                Button button = (Button) view;

                double expTotal = 0;

                if (inputText.length() != 0 && isNumeric(inputText.substring(inputText.length() - 2, inputText.length() - 1))) {
                    if (!isNumeric(button.getText().toString())) {
                        inputTextView.setText(inputTextView.getText() + " " + button.getText() + " ");
                    }
                } else {
                    if (isNumeric(button.getText().toString())) {
                        inputTextView.setText(inputTextView.getText() + " " + button.getText() + " ");
                        expTotal = updateTotal(inputTextView.getText().toString());
                        view.setEnabled(false);
                    }
                }

                // Check if equal to 24
                if (expTotal == 24 && buttonsDisabled()) {
                    button.setText("You win.");
                    startActivity(new Intent(Game.this, Game.class));
                }

            }

            private double updateTotal(String exp) {
                Expression e = new Expression(exp);
                double val = e.solve();
                totalTextView.setText(" = " + val);
                return val;
            }

            private boolean buttonsDisabled() {
                return !(button1.isEnabled() || button2.isEnabled() || button3.isEnabled() || button4.isEnabled());
            }
        };

        View.OnClickListener reset = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTextView.setText("");
                totalTextView.setText(R.string.emptyExpression);
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
            }
        };

        Button add = (Button) findViewById(R.id.addButton);
        Button sub = (Button) findViewById(R.id.subtractButton);
        Button mult = (Button) findViewById(R.id.multiplyButton);
        Button div = (Button) findViewById(R.id.divideButton);

        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(reset);

        button1.setText(nums[0] + "");
        button2.setText(nums[1] + "");
        button3.setText(nums[2] + "");
        button4.setText(nums[3] + "");

        button1.setOnClickListener(updateInput);
        button2.setOnClickListener(updateInput);
        button3.setOnClickListener(updateInput);
        button4.setOnClickListener(updateInput);
        add.setOnClickListener(updateInput);
        sub.setOnClickListener(updateInput);
        mult.setOnClickListener(updateInput);
        div.setOnClickListener(updateInput);

    }

    /**
     * Returns true if total / num is an integer, false otherwise. Assumes total is the larger number.
     */
    private boolean isDivisible(int total, int num) {
        return total % num == 0;
    }

    /**
     * Returns an integer from 0 to 3, representing each of the 4 arithmetic operations
     *
     * @return 0 for addition, 1 for subtraction, 2 for multiplication, and 3 for division
     */
    private int randomOperation() {
        return (int) (Math.random() * 4);
    }

}
