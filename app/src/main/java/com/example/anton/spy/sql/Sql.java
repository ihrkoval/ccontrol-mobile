package com.example.anton.spy.sql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Anton on 20.09.2016.
 */
public class Sql extends SQLiteOpenHelper implements BaseColumns {

    //DATE_COLUMN - уникальная колонка для того что б новости не повторялись
    private static final String DATABASE_NAME = "ccontrol.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "settings";
    public static final String TITLE_COLUMN = "title";
    public static final String VALUE_COLUMN = "value";
    public static final String VALUE2_COLUMN = "image";
    public static final String VALUE3_COLUMN = "description";
    public static final String VALUE4_COLUMN = "link";
    private static final String DATABASE_CREATE= "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + TITLE_COLUMN
            + " text not null , " + VALUE_COLUMN + " text , " + VALUE2_COLUMN // text not null unique,
            + " text , " + VALUE3_COLUMN + " text, " + VALUE4_COLUMN
            + " text);";



    public Sql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Sql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

