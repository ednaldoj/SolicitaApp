package com.solicita;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static Context context;

    public static MyApp getInstance() {
        return (MyApp) context;
    }

    public static Context getContext(){
        return context;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        context = this;
        super.onCreate();
    }
}