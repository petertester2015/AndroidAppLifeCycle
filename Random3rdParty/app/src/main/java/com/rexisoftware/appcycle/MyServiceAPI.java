package com.rexisoftware.appcycle;

import android.os.Binder;

public abstract class MyServiceAPI extends Binder {
    public abstract void report(String txt);
}
