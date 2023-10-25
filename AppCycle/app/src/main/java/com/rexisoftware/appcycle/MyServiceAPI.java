package com.rexisoftware.appcycle;

import android.os.Binder;
import android.os.IBinder;

public abstract class MyServiceAPI extends Binder {
    public abstract void report(String txt);
}
