package com.rexisoftware.appcycle;

import android.os.SystemClock;
import android.util.Log;

public class MyThread extends Thread {
    private final static String TAG = "mythread";
    private volatile boolean mCont = true;
    private String mName = "n/a";
    private volatile int mCounter = 0;

    public MyThread(String name) {
        super(name);
        mName = name;
        Log.i(TAG, "MyThread: " + name);
    }

    public void shutdown() {
        mCont = false;
    }

    public void run() {
        while (mCont) {
            mCounter++;
            Log.i(TAG, "run:" + mName + "=" + mCounter);
            SystemClock.sleep(10000);
        }
        Log.i(TAG, "thread done: " + mName);
    }
}
