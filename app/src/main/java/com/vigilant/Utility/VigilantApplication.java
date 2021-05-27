package com.vigilant.Utility;

import android.app.Application;
import android.content.Context;


public class VigilantApplication extends Application {
    private static VigilantApplication sInstance;
    public boolean lockScreenShow = false;
    public int notificationId = 1989;
    public static Context context;

    public static VigilantApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.context = this;

        sInstance = this;


    }

}
