package adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class myDbAdapter {
    private myDbHelper myHelper;

    public myDbAdapter(Context context) {
        myHelper = new myDbHelper(context);
    }

    public long insertData(String name, String pass, String day) {
        SQLiteDatabase dbb = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.TIME, pass);
        contentValues.put(myDbHelper.DAY, day);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null, contentValues);
        Log.e("ID PRINT", String.valueOf(id));
        return id;
    }

    public Cursor fetch() {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + myDbHelper.TABLE_NAME, null);
        return res;

    }

    public int delete(String name, String day) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String[] whereArgs = {name, day};

        int count = db.delete(myDbHelper.TABLE_NAME, myDbHelper.NAME + " = ? AND " + myDbHelper.DAY + " = ?", whereArgs);
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name;
        private static final String TABLE_NAME = "myTable";   // Table Name;
        private static final int DATABASE_Version = 1;    // Database Version;
        private static final String UID = "_id";     // Column I (Primary Key);
        private static final String NAME = "Name";    //Column II;
        private static final String TIME = "Time";    // Column III;
        private static final String DAY = "Day";      //Column IV;
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255) ," + TIME + " VARCHAR(255) ," + DAY + " VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}