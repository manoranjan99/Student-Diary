package com.manoranjank.studentdiary;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class homenavi extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    dynamicdatamanager homedata;
    SQLiteDatabase db;
    DatabaseManager mDatabaseManager;
    Button markat,markbun,addsubject;
    TextInputEditText addeditsub;
    Boolean check;
    ListView mhomelist;
    Cursor cursor,mCursor;
    mylovelyadapter mhomedapter;
    TextView rollnumber;
    Boolean firstTime=null;
    String mRollnumber="NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homenavi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstTtime();
        mhomelist=(ListView) findViewById(R.id.homelist);
        mDatabaseManager=new DatabaseManager(this);
        homedata=new dynamicdatamanager(this);

        SharedPreferences prefss = getSharedPreferences("Criteria", MODE_PRIVATE);
        mylovelyadapter.criteria=prefss.getInt("percentage",75);


        SharedPreferences roll = getSharedPreferences("rollnumber", MODE_PRIVATE);
        mRollnumber=roll.getString("valueofroll","Hello!");

        loadscreen();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_homenavi);
        rollnumber=(TextView) headerView.findViewById(R.id.rollnumbernavi);
        rollnumber.setText(mRollnumber);
        navigationView.setNavigationItemSelectedListener(this);
        mhomelist=(ListView) findViewById(R.id.homelist);
        mDatabaseManager=new DatabaseManager(this);

        registerForContextMenu(mhomelist);
    }


void firstTtime()
{if (firstTime == null) {
    SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
    firstTime = mPreferences.getBoolean("firstTime", true);
    if (firstTime) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("firstTime", false);

        editor.commit();
      final Intent first = new Intent(getApplicationContext(), onboard.class);
        finish();
                    startActivity(first);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);



    }
  }
}

  public void loadscreen()
    {

       try {

            db = mDatabaseManager.getReadableDatabase();
            cursor = db.query("STDATA", new String[]{"_id", "SUBNAME", "ATTEND", "BUNK"}, null, null, null, null, null);

           mhomedapter = new mylovelyadapter(getApplicationContext(),R.layout.attendcard,cursor,0);
        }catch (SQLException e)
        {
            Toast.makeText(getApplicationContext(),"Sorry ! Technical Error",Toast.LENGTH_SHORT).show();

        }

        mhomelist.setAdapter(mhomedapter);
        registerForContextMenu(mhomelist);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int  position = (int) info.id;
        final int att,bun;
        final String subjectname;

        db = mDatabaseManager.getReadableDatabase();

        mCursor = db.query("STDATA", new String[]{"_id", "SUBNAME", "ATTEND", "BUNK"},
                "_id = ?",new String[] {String.valueOf(position)}, null, null, null);

        if( mCursor != null && mCursor.moveToFirst() ) {
            att = mCursor.getInt(mCursor.getColumnIndex("ATTEND"));
            bun = mCursor.getInt(mCursor.getColumnIndex("BUNK"));
            subjectname = mCursor.getString(mCursor.getColumnIndex("SUBNAME"));
        }else {
            Toast.makeText(getApplicationContext(),"Technical Error",Toast.LENGTH_SHORT).show();
            subjectname="NULL";
            att=0;
            bun=0;
        }


        if (item.getTitle() == "Edit Past Attendance") {
            Intent detailintent=new Intent(getApplicationContext(),subjectdetails.class);
            detailintent.putExtra(subjectdetails.EXTRA_POS,position);
            startActivity(detailintent);

        }else if(item.getTitle()=="Enter Attendance") {
            check = true;
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.inputdialog);
            dialog.setTitle("Attendance");


            markat = (Button) dialog.findViewById(R.id.markat);
            markbun = (Button) dialog.findViewById(R.id.markbunk);


            markat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabaseManager.updatedata(position, (att) + 1, bun);
                    loadscreen();
                    dialog.dismiss();
                }
            });

            markbun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check) {
                        mDatabaseManager.updatedata(position, att, bun + 1);
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        homedata.insertstatus(subjectname, currentDateTimeString);
                        check = false;
                    }
                    loadscreen();
                    dialog.dismiss();


                }
            });



            dialog.show();

        }


        else if(item.getTitle()=="Bunk History")
        {

            Intent bunkstatus = new Intent(getApplicationContext(), bunkstatusm.class);
            bunkstatus.putExtra(bunkstatusm.EXTRA_POSITION, subjectname);
            startActivity(bunkstatus);

        }

        else if(item.getTitle()=="Share")
        {
            PackageManager pm=getPackageManager();
            int  posy = (int) info.id;
            try {

                Intent waIntent = new Intent(Intent.ACTION_SEND);

                waIntent.setType("text/plain");

                String text ="Subject Name : "+subjectname+"\nNO:OF Class Attended:"+att+"\nNO:OF Class Bunked:"+bun;

                PackageInfo minfo=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp");

                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(waIntent, "Share with"));

            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(item.getTitle()=="Delete")
        {
            AlertDialog delete = new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("Are you sure?\nDeleting subject card will delete entire memory captured about the subject.\nPress Cancel to stop the operation")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDatabaseManager.deletedata(position);
                            loadscreen();
                        }
                    })
                    .setNegativeButton("Cancel",null)
                    .create();
            delete.show();

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
        menu.add(0, v.getId(), 0, "Enter Attendance");
        menu.add(0,v.getId(),0,"Bunk History");
        menu.add(0, v.getId(), 0, "Edit Past Attendance");
        menu.add(0, v.getId(), 0, "Share");
        menu.add(0,v.getId(),0,"Delete");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homenavi, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addsub) {

            final Dialog addialog = new Dialog(this);
            addialog.setContentView(R.layout.adddialog);
            addialog.setTitle("Attendance");

            addsubject=(Button)addialog.findViewById(R.id.addsubjecthome);
            addeditsub=(TextInputEditText)addialog.findViewById(R.id.minortext);

            addsubject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 String value=addeditsub.getText().toString();
                    if(TextUtils.isEmpty(value)) {
                        addeditsub.setError("Textfield should not be empty");
                        return;
                    }else {
                        String task = String.valueOf(addeditsub.getText());
                        mDatabaseManager.insertdata(task,0,0);
                        loadscreen();
                    addialog.dismiss();}
                    }

            });

             addialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager= getFragmentManager();

        if (id == R.id.nav_camera) {
            //fragmentManager.beginTransaction().replace(R.id.timecontent,new timetable()).commit();
            Intent timetable=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(timetable);
            // Handle the camera action
        } else if (id == R.id.nav_share) {
            Intent serpercentage=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(serpercentage);

        } else if (id == R.id.nav_send) {
            final Dialog about = new Dialog(this);
            about.setContentView(R.layout.about);
            about.setTitle("About");
            about.show();

        }

         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
