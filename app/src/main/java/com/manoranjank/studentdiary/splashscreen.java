package com.manoranjank.studentdiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class splashscreen extends AppCompatActivity {

        Boolean firstTime=null;


        TextView loadTEXT,loadTEXT2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splashscreen);
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.

            loadTEXT=(TextView) findViewById(R.id.splashtext1);
            loadTEXT2=(TextView) findViewById(R.id.splashtext2);

            Animation myanim= AnimationUtils.loadAnimation(this,R.anim.anim);
            loadTEXT.startAnimation(myanim);
            loadTEXT2.startAnimation(myanim);
            final Intent firstime;


                firstime = new Intent(getApplicationContext(), homenavi.class);
                Thread timer=new Thread() {
                    public void run() {
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finally {
                            startActivity(firstime);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                            finish();
                        }
                    }
                };
                timer.start();

            }


}


