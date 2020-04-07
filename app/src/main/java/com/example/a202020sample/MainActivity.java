package com.example.a202020sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;



public class MainActivity extends AppCompatActivity {

    final Handler customHandles =  new Handler();
    TextView textView2;
    TextView textViewInd;
    Intent intent;

    boolean Active20 = false;
    boolean buttonActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.activity_main);

        textView2 = (TextView) findViewById(R.id.textView3);
        textViewInd = (TextView) findViewById(R.id.textView2);
        intent = new Intent(this, WarningAct.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.appchannel);
            String description = getString(R.string.channeldescriptor);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("101241", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void buttonClicked(View view) {

        Timer timer  = new Timer();

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "M:Tag");

        if (buttonActive) {
            Active20 = true;
            timer.cancel();
            timer.purge();
            Button activateButton = (Button) findViewById(R.id.button);
            activateButton.setEnabled(true);
            activateButton.setText("ACTIVATE!");
            buttonActive = false;
            try {
                wl.release();
            }
            catch (Throwable th) {

            }
        }

        if (Active20 == false) {

            Button activateButton = (Button) findViewById(R.id.button);
            activateButton.setEnabled(false);
            activateButton.setText("Activated");
            buttonActive = true;

            wl.acquire();

            timer.schedule(new TimerTask() {
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "M:Tag");

                @Override
                public void run() {
                    customHandles.post(updater);
                    if (Active20 == true) {
                        cancel();
                        Active20 = false;
                    }
                    wl.acquire();
                }
            }, 0, 1000); //1000

            System.out.println("On: " + "0:00:00");

            final TextView changedData = (TextView) findViewById(R.id.textView);
            Date init = grabDate();
            changedData.setText("Activated On: " + init);
        }
    }

    final Runnable updater =  new Runnable() {

        String time = "";

        @Override
        public void run() {
            clockData.seconds++;

            if (clockData.seconds == 60) {
                clockData.minutes++;
                if (clockData.remainderMins > 1) {
                    clockData.remainderMins--;
                }
                else {
                    if (!clockData.passiveAlert) {
                        startActivity(intent);
                    }
                    else {
                        notifyUser();
                    }

                    clockData.remainderMins = 20;
                    if (!Active20) {
                        Button activateButton = (Button) findViewById(R.id.button);
                        activateButton.setEnabled(true);
                        activateButton.setText("DISABLE");
                       // Active20 = true;
                    }
                }
                textViewInd.setText("20/20/20 in: " + clockData.remainderMins + " MINUTES");
                clockData.seconds = 0;
            }

            if (clockData.minutes == 60) {
                clockData.hours++;
                clockData.minutes = 0;
            }

            if ((clockData.minutes <= 10)&&(clockData.seconds <= 10)) {
                time = clockData.hours + ":" + 0 + clockData.minutes + ":" + 0 + clockData.seconds;
            }
            if ((clockData.minutes >= 10)&&(clockData.seconds <= 10)) {
                time = clockData.hours + ":" + clockData.minutes + ":" + 0 + clockData.seconds;
            }
            if ((clockData.minutes <= 10)&&(clockData.seconds >= 10)) {
                time = clockData.hours + ":" + 0 + clockData.minutes + ":" + clockData.seconds;
            }

            if ((clockData.minutes >= 10)&&(clockData.seconds >= 10)) {
                time = clockData.hours + ":" + clockData.minutes + ":" + clockData.seconds;
            }
            clockData.elapsedTime = time;
           // System.out.println("On: " + time);
            textView2.setText(clockData.elapsedTime);

        }
    };

    public Date grabDate() {
        Date formattedDate = new Date();
        return formattedDate;
    }

    public void settingsClicked(View view) {
        Intent loadSettings = new Intent(this,SettingsActivity.class);
        startActivity(loadSettings);
    }

    public void notifyUser() {
        final MediaPlayer mediaplayer = MediaPlayer.create(this, R.raw.alert1);
        mediaplayer.start();

        TaskStackBuilder stackBuilder =  TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "101241")
                .setSmallIcon(R.drawable.mainicon)
                .setContentTitle("20/20/20!")
                .setContentText("It's 20/20/20! Look around and far for at least 20 metres for twenty seconds.")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(resultIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(Integer.parseInt("1241"), builder.build());
    }
}
