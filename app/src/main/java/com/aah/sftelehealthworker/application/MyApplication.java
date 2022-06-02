package com.aah.sftelehealthworker.application;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication applicationInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationInstance = this;
    }

    public static MyApplication getMyApplicationInstance(){
        return applicationInstance;
    }

}
