package com.rexisoftware.appcycle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private final static String TAG = "myservice";
    private final MyThread mThread;
    private final IBinder mBinder = new LocalBinder();

    public MyService() {
        Log.i(TAG, "MyService");
        mThread = new MyThread(TAG);
        mThread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return mBinder;
    }

    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "MyService.onStartCommand: intent=" + intent.toString() + " flags=" + flags + " startId=" + startId);
        String extra = intent.getStringExtra("rnd");
        if (extra == null) extra = "n/a";
        Log.i(TAG, "extra=" + extra);
        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        mThread.shutdown();
    }

    public class LocalBinder extends MyServiceAPI {
        MyService getService() {
            Log.i(TAG, "LocalBinder.getService");
            // Return this instance of LocalService so clients can call public methods.
            return MyService.this;
        }

        @Override
        public void report(String txt) {
            Log.i(TAG, "received report: " + txt);
        }
    }
}