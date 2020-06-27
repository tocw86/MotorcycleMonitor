package com.software4bikers.motorcyclerun.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.software4bikers.motorcyclerun.helpers.DbHelper;

public class RunSessionDataModel {

    private Context context;
    private DbHelper dbHelper;
    private  SQLiteDatabase db;

    public RunSessionDataModel(Context context){
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = this.dbHelper.getWritableDatabase();
    }

   public void create(String userId, String sessionId, String lat, String lon, String ele, String roll, String speed ,String createdAt, String updatedAt){
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(RunSessionDataModel.RunSessionData.COLUMN_NAME_USER_ID, userId);
        values.put(RunSessionDataModel.RunSessionData.COLUMN_NAME_SESSION_ID, sessionId);
        values.put(RunSessionDataModel.RunSessionData.COLUMN_NAME_LAT, lat);
        values.put(RunSessionDataModel.RunSessionData.COLUMN_NAME_LON, lon);
        values.put(RunSessionDataModel.RunSessionData.COLUMN_NAME_ELE, ele);
        values.put(RunSessionDataModel.RunSessionData.COLUMN_NAME_ROLL, roll);
        values.put(RunSessionDataModel.RunSessionData.COLUMN_NAME_SPEED, speed);
        values.put(RunSessionDataModel.RunSessionData.COLUMN_NAME_CREATED_AT, createdAt);
        values.put(RunSessionDataModel.RunSessionData.COLUMN_NAME_UPDATED_AT, updatedAt);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(RunSessionDataModel.RunSessionData.TABLE_NAME, null, values);
    }

    public static class RunSessionData implements BaseColumns {
        public static final String TABLE_NAME = "run_sessions_data";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_SESSION_ID = "session_id";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LON = "lon";
        public static final String COLUMN_NAME_ELE = "ele";
        public static final String COLUMN_NAME_ROLL = "roll";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_UPDATED_AT = "updated_at";
    }
}
