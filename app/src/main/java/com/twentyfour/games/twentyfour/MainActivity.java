package com.twentyfour.games.twentyfour;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView title = (TextView) findViewById(R.id.titleText);

        Button startButton = (Button) findViewById(R.id.startButton);
        Button optionsButton = (Button) findViewById(R.id.optionsButton);

        View.OnClickListener optionsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("24");
                title.setTextColor(Color.rgb(rand(255), rand(255), rand(255)));
            }
            private int rand(int n) {
                return (int) (n * Math.random());
            }
        };
        optionsButton.setOnClickListener(optionsClickListener);

        View.OnClickListener startClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        };
        startButton.setOnClickListener(startClickListener);
    }
}
