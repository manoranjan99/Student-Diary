package com.manoranjank.studentdiary;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class subjectdetails extends AppCompatActivity {

    Button mAttenplus, mAttenminus, mBunkplus, mBunkminus;
    TextView mAttenum, mBunknum, mPercentage;
    ProgressBar mProgressBar;
    int attenum, bunknum, total,status;
    int bunkstatus;
    RelativeLayout mRelativeLayout;
    DatabaseManager mylove;
    SQLiteDatabase mylite;
    Cursor nsign;
    int posistad;
    public static final String EXTRA_POS="Address";
    String mfinal, mifinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectdetails);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAttenum = (TextView) findViewById(R.id.attenum);
        mAttenminus = (Button) findViewById(R.id.attenminus);
        mAttenplus = (Button) findViewById(R.id.attenplus);
        mPercentage = (TextView) findViewById(R.id.percentage);
        mBunknum = (TextView) findViewById(R.id.bunknum);
        mBunkminus = (Button) findViewById(R.id.bunkminus);
        mBunkplus = (Button) findViewById(R.id.bunkplus);
        mylove=new DatabaseManager(this);
        posistad=(Integer) getIntent().getExtras().get(EXTRA_POS);
        setpref();

        mAttenum.setText(String.valueOf(attenum));
        mBunknum.setText(String.valueOf(bunknum));
        if (attenum == 0 && bunknum == 0) {
            total = 0;
            mPercentage.setText(String.valueOf(total));


        } else {
            total = (attenum * 100 / (attenum + bunknum));
            mPercentage.setText(String.valueOf(total));
        }
        mProgressBar.setProgress(total);



        mAttenplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setattendata();
                attenum += 1;
                mAttenum.setText(String.valueOf(attenum));
                setpercentage();

            }
        });


        mAttenminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setattendata();
                if (attenum > 0) {
                    attenum -= 1;

                }
                mAttenum.setText(String.valueOf(attenum));
                setpercentage();

            }
        });

        mBunkplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setbunkdata();
                bunknum += 1;
                mBunknum.setText(String.valueOf(bunknum));
                setpercentage();
            }
        });

        mBunkminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setbunkdata();
                if (bunknum > 0) {
                    bunknum -= 1;
                }
                mBunknum.setText(String.valueOf(bunknum));
                setpercentage();
            }
        });

        mProgressBar.setProgress(total);

    }

    @Override
    public void onBackPressed() {

        savedata();
        setBunkstatus();
        Toast.makeText(getApplicationContext(),"Saved ! Restart App",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }


    private void setattendata() {
        mfinal = mAttenum.getText().toString();
        attenum = Integer.valueOf(mfinal);
    }

    private void setbunkdata() {
        mifinal = mBunknum.getText().toString();
        bunknum = Integer.valueOf(mifinal);
    }


    private void setpercentage() {
        mfinal = mAttenum.getText().toString();
        attenum = Integer.valueOf(mfinal);
        mifinal = mBunknum.getText().toString();
        bunknum = Integer.valueOf(mifinal);


        if (attenum == 0 && bunknum == 0) {
            total = 0;
            mPercentage.setText(String.valueOf(total));
        } else {
            total = (attenum * 100 / (attenum + bunknum));
            mPercentage.setText(String.valueOf(total));
        }
        mProgressBar.setProgress(total,true);

    }


    private void setpref()
    {
        mylite=mylove.getReadableDatabase();
        nsign= mylite.query("STDATA", new String[]{"_id", "SUBNAME", "ATTEND", "BUNK"},
                "_id = ?",new String[] {String.valueOf(posistad)}, null, null, null);

        if( nsign != null && nsign.moveToFirst() ){
            attenum=nsign.getInt(nsign.getColumnIndex("ATTEND"));
            bunknum=nsign.getInt(nsign.getColumnIndex("BUNK"));

        }
    }

    private void savedata()
    {
            mylove.updatedata(posistad,attenum,bunknum);
    }

    public void setBunkstatus()
    {

        status=attenum-3*(bunknum);

        if (status<0)
        {
            bunkstatus= status* (-1);
        }
        else
        {
            bunkstatus=0;
        }

    }

}
