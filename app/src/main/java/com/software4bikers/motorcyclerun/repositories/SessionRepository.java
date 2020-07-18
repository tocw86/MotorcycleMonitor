package com.software4bikers.motorcyclerun.repositories;

import android.database.Cursor;

import com.software4bikers.motorcyclerun.helpers.Helper;
import com.software4bikers.motorcyclerun.models.data.SessionsData;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class SessionRepository {
    public static ArrayList<SessionsData> getSessionsData(Cursor res) {
        ArrayList<SessionsData> tmp = new ArrayList<SessionsData>();
        while (res.moveToNext()){
            SessionsData sessionsData = new SessionsData(res.getString(0), res.getString(1), res.getString(2),res.getString(3));
            tmp.add(sessionsData);
        }
        return tmp;
    }

    public static ArrayList<GeoPoint> getSessionDataModel(Cursor res, String sessionId) {
        ArrayList<GeoPoint> tmp = new ArrayList<GeoPoint>();
        while (res.moveToNext()){
            GeoPoint geoPoint = new GeoPoint(Double.valueOf(res.getString(0)), Double.valueOf(res.getString(1)));
            tmp.add(geoPoint);
        }
        return tmp;
    }

    public static String getAvgSpeedFromSqlData(Cursor res) {
        List<Integer> tmp = new ArrayList<Integer>();
        while (res.moveToNext()){
            int speed = Integer.parseInt(res.getString(0));
            tmp.add(speed);
        }
        double avg = Helper.median(tmp);
        String avgSpeed = String.valueOf(avg);
        return  avgSpeed + " km/h";
    }
}
