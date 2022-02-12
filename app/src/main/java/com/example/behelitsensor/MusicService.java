package com.example.behelitsensor;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service implements SensorEventListener{

    SensorManager sensorManager;
    Sensor sensorLight;
    private MediaPlayer mPlayer;
    Play p;


    public MusicService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MediaPlayer.create(this, R.raw.behelit);
        mPlayer.setVolume(50,50);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener((SensorEventListener) this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        p = new Play("playMusic");
        p.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        p.endWork();
        sensorManager.unregisterListener((SensorEventListener) this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] > 300){ p.pauseMusic();}
        else{ p.onPrepared();}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class Play extends Thread {
        String threadName;

        public Play(String name) {
            threadName = name;
        }

        @Override
        public void run() {

        }

        public void onPrepared() {
            mPlayer.start();
        }

        public void pauseMusic(){ mPlayer.pause();}

        public void endWork(){
            mPlayer.stop();
            mPlayer.release();
            this.interrupt();
        }
    }
}