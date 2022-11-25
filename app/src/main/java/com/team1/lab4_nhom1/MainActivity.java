package com.team1.lab4_nhom1;

import static com.team1.lab4_nhom1.Notification.CHANNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton play, stop;

    SeekBar seekBar;

    TextView lyrics;

    boolean state = true;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();

        addEvents();
    }


    private void addEvents() {

        final Intent intent = new Intent(MainActivity.this, MyService.class);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state == true){
                    play.setImageResource(R.drawable.pause);
                    sendNotification(intent);
                    startService(intent);
                    state = false;
                }else{
                    play.setImageResource(R.drawable.play);
                    startService(intent);
                    state = true;
                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.setImageResource(R.drawable.play);
                state = true;
                stopService(intent);
            }
        });
    }

    private void sendNotification(Intent intent) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);

        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "tag");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                // Add media control buttons that invoke intents in your media service
                .addAction(R.drawable.ic_prev, "Previous", null) // #0
                .addAction(R.drawable.ic_pause, "Pause", pendingIntent)  // #1
                .addAction(R.drawable.ic_next, "Next", null)     // #2
                // Apply the media style template
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1 /* #1: pause button */)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setContentTitle("My music")
                .setContentText("Waiting for you")
                .setLargeIcon(bitmap)
                .build();

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(    1 , notification);
    }

    private void addControls() {

        play = this.<ImageButton>findViewById(R.id.button_play);

        stop = this.<ImageButton>findViewById(R.id.button_stop);
        lyrics = this.<TextView>findViewById(R.id.lyrics);

        seekBar = this.<SeekBar>findViewById(R.id.seekbar);

    }
}