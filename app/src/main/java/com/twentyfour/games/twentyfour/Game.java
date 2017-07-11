package com.twentyfour.games.twentyfour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        final TextView input = (TextView) findViewById(R.id.input);

        View.OnClickListener updateInput = new View.OnClickListener() {
            public void onClick(View view) {
                Button button = (Button) view;
                input.setText("" + input.getText() + " " + button.getText() + " ");
            }
        };

        Button button1 = (Button) findViewById(R.id.num1);
        Button button2 = (Button) findViewById(R.id.num2);
        Button button3 = (Button) findViewById(R.id.num3);
        Button button4 = (Button) findViewById(R.id.num4);

        Button add = (Button) findViewById(R.id.addButton);
        Button sub = (Button) findViewById(R.id.subtractButton);
        Button mult = (Button) findViewById(R.id.multiplyButton);
        Button div = (Button) findViewById(R.id.divideButton);

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

        System.out.println(nums[0]);
        System.out.println(nums[1]);
        System.out.println(nums[2]);
        System.out.println(nums[3]);

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
