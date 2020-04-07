package com.example.a202020sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Settings");
        setContentView(R.layout.activity_settings);

        if (clockData.passiveAlert) {
            Switch toggleSwitch = (Switch) findViewById(R.id.switch1);
            toggleSwitch.setChecked(true);
        }
        else {
            Switch toggleSwitch = (Switch) findViewById(R.id.switch1);
            toggleSwitch.setChecked(false);
        }
    }

    public void bbaseClicked(View view) {
        finish();
    }

    public void switchToggled(View view) {
        Switch toggleSwitch = (Switch) findViewById(R.id.switch1);
        if (toggleSwitch.isChecked()) {
            clockData.passiveAlert = true;
        }
        else {
            clockData.passiveAlert = false;
        }
    }


    public void resetBtn(View view) {
        clockData.Reset();
    }
}
