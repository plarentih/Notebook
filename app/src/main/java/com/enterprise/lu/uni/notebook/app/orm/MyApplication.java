package com.enterprise.lu.uni.notebook.app.orm;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Plarent on 10/21/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
