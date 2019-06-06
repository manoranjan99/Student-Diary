package com.manoranjank.studentdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Manoranjan K on 28-05-2019.
 */

public class dynamicdatamanager extends SQLiteOpenHelper {

    private static final String DB_NAME = "dytable";
    private static final int DB_VERSION = 1;


    dynamicdatamanager(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }



    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BUNKSTATUS("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"SUBNAME TEXT, "
                +"BUNKDATE TEXT );");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE BUNKSTATUS("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"SUBNAME TEXT, "
                +"BUNKDATE TEXT );");

    }

    public void insertstatus(String subname,String bunkdate)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues mDetails=new ContentValues();
        mDetails.put("SUBNAME",subname);
        mDetails.put("BUNKDATE",bunkdate);
        db.insert("BUNKSTATUS",null,mDetails);
        db.close();
    }

    public void deletestatus(int ad)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("BUNKSTATUS", "_id = ?",new String[]{String.valueOf(ad)});
    }
}
