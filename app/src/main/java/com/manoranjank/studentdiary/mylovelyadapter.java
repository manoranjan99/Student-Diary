package com.manoranjank.studentdiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Manoranjan K on 25-05-2019.
 */

public class mylovelyadapter extends ResourceCursorAdapter {


public static int criteria=75;

    public mylovelyadapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String mfinal,mifinal;
        int attenum,bunknum,total;
        TextView sub = (TextView) view.findViewById(R.id.Subname);
        sub.setText(cursor.getString(cursor.getColumnIndex("SUBNAME")));


        TextView attend = (TextView) view.findViewById(R.id.Attend);
        attenum=cursor.getInt(cursor.getColumnIndex("ATTEND"));
        String attended=String.valueOf(attenum)+" Attend";
        attend.setText(attended);

        TextView bunk = (TextView) view.findViewById(R.id.Bunk);
        bunknum=cursor.getInt(cursor.getColumnIndex("BUNK"));
        String bunked=String.valueOf(bunknum)+" Bunks";
        bunk.setText(bunked);


        TextView warning=(TextView) view.findViewById(R.id.warning);
        TextView percentage=(TextView)view.findViewById(R.id.Percentage);

        if (attenum == 0 && bunknum == 0) {
            total = 0;
            String display= String.valueOf(total)+"%";
            percentage.setText(display);
        } else {
            total = (attenum * 100 / (attenum + bunknum));
            String display= String.valueOf(total)+"%";
            percentage.setText(display);
        }

        int warningtext=setBunkstatus(attenum,bunknum);
        String warningstringtext="You need to attend " + String.valueOf(warningtext) + " classes";
        warning.setText(warningstringtext);

    }

    private int setBunkstatus(int attenum,int bunknum)
    {
        double percentage=((double)criteria/100);
        double status = ((percentage)*bunknum)/(1-percentage);
        int bunkstatus= ((int)(status))-attenum;
        if (bunkstatus < 0) {
            bunkstatus = 0;
        }
        return bunkstatus;
    }



}
