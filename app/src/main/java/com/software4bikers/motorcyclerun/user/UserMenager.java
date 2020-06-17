package com.software4bikers.motorcyclerun.user;

import android.content.SharedPreferences;

import java.util.UUID;

public class UserMenager {
    public static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    private SharedPreferences sharedPreferences;

    public UserMenager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String generateUserId(){
        String userId  = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PREF_UNIQUE_ID, userId);
            editor.apply();
        return userId;
    }

    public String getUserId(){
        String userId = sharedPreferences.getString(PREF_UNIQUE_ID, "");
        if(userId.isEmpty()){
            return generateUserId();
        }else{
            return userId;
        }
    }

}
