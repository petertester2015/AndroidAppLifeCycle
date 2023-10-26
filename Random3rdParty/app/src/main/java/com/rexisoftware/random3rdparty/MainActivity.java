package com.rexisoftware.random3rdparty;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "random3rd";
    private final Random mRnd = new Random();
    Handler h;
    Runnable r = new Runnable() {
        @Override
        public void run() {
            myStartService();
        }
    };
    MyThread mThread;
    private Messenger mService1;
    private final ServiceConnection mConn1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                mService1 = new Messenger(service);
                Log.i(TAG, "onServiceConnected1: " + service.toString() + " " + service.getInterfaceDescriptor() + " " + service.isBinderAlive());
            } catch (Throwable e) {
                Log.i(TAG, e.toString());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected1");
            mService1 = null;
        }
    };
    private Messenger mService2;
    private final ServiceConnection mConn2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                mService2 = new Messenger(service);
                Log.i(TAG, "onServiceConnected2: " + service.toString() + " " + service.getInterfaceDescriptor() + " " + service.isBinderAlive());
            } catch (Throwable e) {
                Log.i(TAG, e.toString());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected2");
            mService2 = null;
        }
    };

    private String getRandomString() {
        int x = mRnd.nextInt();
        if (x < 0) x = -x;
        return "" + x;
    }

    public void callback(int x) {
        Log.i(TAG, "callback...");
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                callbackImpl(x);
            }
        };
        h.post(r2);
        Log.i(TAG, "callback.");
    }

    private void callbackImpl(int x) {
        Log.i(TAG, "callbackImpl: " + x + "...");
        Intent i = new Intent();
        i.setComponent(new ComponentName("com.rexisoftware.appcycle", "com.rexisoftware.appcycle.MyService"));
        i.putExtra("counter", x);
        boolean b = getApplicationContext().bindService(i, mConn1, BIND_AUTO_CREATE);
        Log.i(TAG, "callbackImpl1: " + b);
        i = new Intent();
        i.setComponent(new ComponentName("com.rexisoftware.appcycle", "com.rexisoftware.appcycle.MyServiceIso"));
        i.putExtra("counter", x);
        b = getApplicationContext().bindService(i, mConn2, BIND_AUTO_CREATE);
        Log.i(TAG, "callbackImpl2: " + b);
        try {
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putInt("counter", x);
            msg.setData(bundle);
            mService1.send(msg);
        } catch (Throwable t) {
            Log.i(TAG, "callbackImpl1: " + t);
        }
        try {
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putInt("counter", x);
            msg.setData(bundle);
            mService2.send(msg);
        } catch (Throwable t) {
            Log.i(TAG, "callbackImpl2: " + t);
        }
        Log.i(TAG, "callbackImpl: " + x + " done.");
    }

    private void myStartService() {
        Intent i = new Intent();
        i.setComponent(new ComponentName("com.rexisoftware.appcycle", "com.rexisoftware.appcycle.MyService"));
        String str = getRandomString();
        i.putExtra("rnd", str);
        Log.i(TAG, "myStartService1: " + str);
        getApplicationContext().startService(i);
        SystemClock.sleep(1000);
        i.setComponent(new ComponentName("com.rexisoftware.appcycle", "com.rexisoftware.appcycle.MyServiceIso"));
        str = getRandomString();
        i.putExtra("rnd", str);
        Log.i(TAG, "myStartService2: " + str);
        getApplicationContext().startService(i);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");
        h = new Handler();
        mThread = new MyThread(TAG);
        mThread.setActivity(this);
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
        try {
            unbindService(mConn1);
        } catch (Throwable t) {

        }
        try {
            unbindService(mConn2);
        } catch (Throwable t) {

        }
    }
}
