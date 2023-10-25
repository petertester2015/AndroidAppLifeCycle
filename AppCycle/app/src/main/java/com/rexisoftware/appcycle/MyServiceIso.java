package com.rexisoftware.appcycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyServiceIso extends Service {
    private final static String TAG = "myserviceiso";
    private final MyThread mThread;

    public MyServiceIso() {
        Log.i(TAG, "MyServiceIso");
        mThread = new MyThread(TAG);
        mThread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "MyService.onStartCommand: intent=" + intent.toString() + " flags=" + flags + " startId=" + startId);
        String extra = intent.getStringExtra("rnd");
        if (extra == null)
            extra = "n/a";
        Log.i(TAG, "extra=" + extra);
        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        mThread.shutdown();
    }
}