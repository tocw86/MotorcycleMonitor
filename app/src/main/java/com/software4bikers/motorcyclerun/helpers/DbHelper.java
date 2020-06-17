package com.software4bikers.motorcyclerun.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.software4bikers.motorcyclerun.sqlite.RunSessionModel;

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Software4BikersSchema.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RunSessionModel.RunSession.TABLE_NAME + " (" +
                    RunSessionModel.RunSession._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    RunSessionModel.RunSession.COLUMN_NAME_USER_ID + " TEXT DEFAULT NULL," +
                    RunSessionModel.RunSession.COLUMN_NAME_CREATED_AT + " TEXT DEFAULT NULL," +
                    RunSessionModel.RunSession.COLUMN_NAME_UPDATED_AT + " TEXT DEFAULT NULL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " +  RunSessionModel.RunSession.TABLE_NAME;



    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
