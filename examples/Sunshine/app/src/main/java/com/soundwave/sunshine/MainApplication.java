package com.soundwave.sunshine;

import android.app.Application;

import com.soundwave.shine.Shine;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Shine.enableLogging();
        Shine.initialize(this, "YOUR_DEV_KEY");
    }
}
