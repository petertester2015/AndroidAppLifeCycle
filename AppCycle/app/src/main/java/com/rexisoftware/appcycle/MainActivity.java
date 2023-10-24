package com.rexisoftware.appcycle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "mainact";
    Handler h;
    Runnable r = new Runnable() {
        @Override
        public void run() {
            myStartService();
        }
    };
    MyThread mThread;

    private void myStartService() {
        Log.i(TAG, "myStartService1");
        startService(new Intent(MainActivity.this, MyService.class));
        SystemClock.sleep(1000);
        Log.i(TAG, "myStartService2");
        startService(new Intent(MainActivity.this, MyServiceIso.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");
        h = new Handler();
        mThread = new MyThread(TAG);
        mThread.start();
    }

    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        h.postDelayed(r, 5000);
    }

    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        mThread.shutdown();
    }

    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }
}