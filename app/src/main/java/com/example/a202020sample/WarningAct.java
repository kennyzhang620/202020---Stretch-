package com.example.a202020sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class WarningAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.activity_warning);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        final MediaPlayer mediaplayer = MediaPlayer.create(this, R.raw.alert1);
        mediaplayer.start();
    }

    public void buttonActivated(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        finish();
    }

    public void notifyUser() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "101241")
                .setSmallIcon(R.drawable.mainicon)
                .setContentTitle("20/20/20!")
                .setContentText("It's 20/20/20! Look around and far for at least 20 metres for twenty seconds. Tap to go to app.")
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(Integer.parseInt("1241"), builder.build());
    }
}
