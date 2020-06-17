package com.software4bikers.motorcyclerun.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.software4bikers.motorcyclerun.user.UserMenager;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private static Context appContext;
    private UserMenager userMenager;
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userMenager = new UserMenager(sharedPreferences);
        appContext = getApplicationContext();
    }

    public UserMenager getUserMenager(){
        return userMenager;
    }

    public static Context getAppContext() {
        return appContext;
    }
}