package com.example.angad.stackoverflow;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Angad on 17/04/16.
 */
public class FileManagerHelper extends SQLiteOpenHelper {
    public static final String FAV_TABLE = "favorites";
    public static final String FAV_TABLE_ID = "_ID";
    public  static  final  String DISPLAY_NAME="name";
    public  static final String PROFILE_PIC="pic";
    public static final String TITLE="title";
    public static final String TAGS="tags";
    public  static final String LINK="link";
    public  static final String SCORE="score";
    public static final String TIME="time";
   public static final String CREATE="CREATE TABLE "+FAV_TABLE+" ("+FAV_TABLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ DISPLAY_NAME+" VARCHAR(255),"+ PROFILE_PIC+" VARCHAR(255),"+ TITLE+" VARCHAR(255),"+ TAGS+" VARCHAR(255),"+ SCORE+" INTEGER ,"+ LINK+" VARCHAR(255),"+TIME +" LONG);";

    public FileManagerHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "file_manager", factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

     db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
