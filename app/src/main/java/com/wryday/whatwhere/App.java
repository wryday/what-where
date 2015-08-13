package com.wryday.whatwhere;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "App Created.");

        instance = this;
    }

    public static Context getAppContext() {
        return instance;
    }
}
