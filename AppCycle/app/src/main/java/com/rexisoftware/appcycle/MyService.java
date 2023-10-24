package com.rexisoftware.appcycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private final static String TAG = "myservice";
    private MyThread mThread;

    public MyService() {
        Log.i(TAG, "MyService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate1");
        mThread = new MyThread(TAG);
        mThread.start();
        Log.i(TAG, "onCreate2");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "MyService.onStartCommand: intent=" + intent.toString() + " flags=" + flags + " startId=" + startId);
        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        mThread.shutdown();
    }
}