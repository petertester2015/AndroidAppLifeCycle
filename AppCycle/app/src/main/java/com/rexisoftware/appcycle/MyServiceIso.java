package com.rexisoftware.appcycle;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class MyServiceIso extends Service {
    private final static String TAG = "myserviceiso";
    private final MyThread mThread;
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage=" + msg.toString());
            Bundle bundle = msg.getData();
            if (bundle!=null){
                int x = bundle.getInt("counter");
                Log.i(TAG, "counter=" +x);
            }
        }
    }
    final Messenger mMessenger = new Messenger(new MyServiceIso.IncomingHandler());

    public MyServiceIso() {
        Log.i(TAG, "MyServiceIso");
        mThread = new MyThread(TAG);
        mThread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return mMessenger.getBinder();
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