package com.software4bikers.motorcyclerun.repositories;

import android.database.Cursor;
import android.util.Log;

import com.software4bikers.motorcyclerun.models.SessionsData;

import java.util.ArrayList;

public class SessionRepository {
    public static ArrayList<SessionsData> getSessionsData(Cursor res) {
        ArrayList<SessionsData> tmp = new ArrayList<SessionsData>();
        while (res.moveToNext()){
            SessionsData sessionsData = new SessionsData(res.getString(0), res.getString(1), res.getString(2),res.getString(3));
            tmp.add(sessionsData);
        }
        return tmp;
    }
}
