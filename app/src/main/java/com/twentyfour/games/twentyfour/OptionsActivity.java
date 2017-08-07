package com.twentyfour.games.twentyfour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import static com.twentyfour.games.twentyfour.MainActivity.PREFS_NAME;

public class OptionsActivity extends AppCompatActivity {

    Switch mSwitch1;
    Switch mSwitch2;

    Intent mSoundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        mSwitch1 = (Switch) findViewById(R.id.switch1);
        mSwitch2 = (Switch) findViewById(R.id.switch2);

        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        final boolean silent = settings.getBoolean("silentMode", true);
        mSwitch1.setChecked(!silent);
        mSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (silent) {
                    mSoundService = new Intent(getBaseContext(), BackgroundSoundService.class);
                    startService(mSoundService);
                } else {
    
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("silentMode", !mSwitch1.isChecked());

        // Commit the edits!
        editor.apply();
    }
}
