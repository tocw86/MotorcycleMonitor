package com.software4bikers.motorcyclerun.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.software4bikers.motorcyclerun.helpers.DbHelper;

import java.util.List;

public class RunSessionModel {

    private Context context;
    private DbHelper dbHelper;
    private  SQLiteDatabase db;

    public RunSessionModel(Context context){
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
        this.db = this.dbHelper.getWritableDatabase();
    }

    public long create(String userId, String createdAt, String updatedAt){
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(RunSessionModel.RunSession.COLUMN_NAME_USER_ID, userId);
        values.put(RunSessionModel.RunSession.COLUMN_NAME_CREATED_AT, createdAt);
        values.put(RunSessionModel.RunSession.COLUMN_NAME_UPDATED_AT, updatedAt);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(RunSessionModel.RunSession.TABLE_NAME, null, values);
        return newRowId;
    }

    public Cursor getAllSessions(){
            Cursor res = this.db.rawQuery("SELECT * FROM " + RunSessionModel.RunSession.TABLE_NAME, null);
            return res;
    }

    public static class RunSession implements BaseColumns {
        public static final String TABLE_NAME = "run_sessions";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_UPDATED_AT = "updated_at";
    }
}
