package com.manoranjank.studentdiary;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class bunkstatusm extends AppCompatActivity {


    ListView bunklist;
    SQLiteDatabase getbunkdata;
    dynamicdatamanager viewdata,mHelper;
    Cursor bunkcursor;
    String addressval;
    public static String EXTRA_POSITION="address";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunkstatusm);

        bunklist=(ListView) findViewById(R.id.bunklist);
        addressval=(String) getIntent().getExtras().get(EXTRA_POSITION);
        loadlist();

    }

    void loadlist()
    {
        viewdata=new dynamicdatamanager(this);
        try
        {
            getbunkdata = viewdata.getReadableDatabase();
            Toast.makeText(getApplicationContext(),addressval,Toast.LENGTH_SHORT).show();
            bunkcursor=getbunkdata.query("BUNKSTATUS",new String[] {"_id","SUBNAME","BUNKDATE"},"SUBNAME = ?",new String[] {addressval},null,null,null);
            SimpleCursorAdapter myloveadps=new SimpleCursorAdapter(getApplicationContext(),
                    android.R.layout.simple_list_item_1,bunkcursor,new String[]{"BUNKDATE"},new int[]{android.R.id.text1} ,0);
            bunklist.setAdapter(myloveadps);
        }catch (SQLException e)
        {
            Toast.makeText(getApplicationContext(),"Sorry ! Technical Error",Toast.LENGTH_SHORT).show();

        }

        registerForContextMenu(bunklist);

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int  posy = (int) info.id;

        if (item.getTitle() == "Delete") {
            mHelper=new dynamicdatamanager(this);

            int  position = (int) info.id;

            Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();

            mHelper.deletestatus(posy);
            loadlist();



        }
        else {
            return  false;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Menu");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getbunkdata.close();
        bunkcursor.close();
    }
}


