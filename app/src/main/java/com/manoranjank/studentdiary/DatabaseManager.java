package com.manoranjank.studentdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Manoranjan K on 25-05-2019.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "Data";
    private static final int DB_VERSION = 1;

    DatabaseManager(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE STDATA("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"SUBNAME TEXT, "
                +"ATTEND INTEGER, "
                +"BUNK INTERGER );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STDATA");
        onCreate(db);
    }

    public void insertdata(String subname,int Attend,int Bunk)
    {
         SQLiteDatabase db=this.getWritableDatabase();
        ContentValues mDetails=new ContentValues();
        mDetails.put("SUBNAME",subname);
        mDetails.put("ATTEND",Attend);
        mDetails.put("BUNK",Bunk);
         db.insert("STDATA",null,mDetails);
       //  db.close();
    }





    public void updatedata(int iid,int newatt,int newbunk)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues updetails=new ContentValues();
        updetails.put("ATTEND",newatt);
        updetails.put("BUNK",newbunk);
        db.update("STDATA",updetails,"_id=?",new String[]{String.valueOf(iid)});

    }
    public void deletedata(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("STDATA", "_id = ?",new String[]{String.valueOf(id)});
    }

}
