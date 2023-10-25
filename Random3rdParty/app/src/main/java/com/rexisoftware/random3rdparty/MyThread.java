package com.rexisoftware.random3rdparty;

import android.os.SystemClock;
import android.util.Log;

public class MyThread extends Thread {
    private final static String TAG = "mythread3rd";
    private volatile boolean mCont = true;
    private String mName = "n/a";
    private volatile int mCounter = 0;
    private MainActivity mActivity;

    public MyThread(String name) {
        super(name);
        mName = name;
        Log.i(TAG, "MyThread: " + name);
    }

    public void setActivity(MainActivity a){
        mActivity = a;
    }

    public void shutdown() {
        mCont = false;
    }

    public void run() {
        while (mCont) {
            mCounter++;
            Log.i(TAG, "run:" + mName + "=" + mCounter);
            if (mActivity != null){
                mActivity.callback(mCounter);
            }
            SystemClock.sleep(30000);
        }
        Log.i(TAG, "thread done: " + mName);
    }
}
