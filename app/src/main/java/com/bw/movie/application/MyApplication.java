package com.bw.movie.application;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();

    }
    public static Context MyApplication() {
        return context;
    }
}
