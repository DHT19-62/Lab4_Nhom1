package com.team1.lab4_nhom1;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //  Ham khoi tao
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(MyService.this, R.raw.waitingforyou);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (player.isPlaying()){
            player.pause();
        }else{
            player.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player.isPlaying()){
            player.stop();
        }
    }

    /** method for clients */
    public int getMusicDuration() {
        return player.getDuration();
    }

    public int getMusicCurPos(){
        return player.getCurrentPosition();
    }

    public void seekToPos (int pos){
        player.seekTo(pos);
    }
}