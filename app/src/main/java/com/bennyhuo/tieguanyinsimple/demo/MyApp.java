package com.bennyhuo.tieguanyinsimple.demo;

import android.app.Application;

import com.bennyhuo.tieguanyinsimple.runtime.ActivityBuilder;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActivityBuilder.INSTANCE.init(this);
    }
}
