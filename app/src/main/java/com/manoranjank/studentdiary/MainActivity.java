package com.manoranjank.studentdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    SeekBar mSeekBar;
    TextView percent;
    Button mButton;
    int prog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeekBar=(SeekBar) findViewById(R.id.seekBar);
        percent=(TextView) findViewById(R.id.percettext);
        mButton=(Button) findViewById(R.id.setpercentage);

        mSeekBar.incrementProgressBy(5);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String placeholder=progress + "%";
                percent.setText(placeholder);
                prog=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylovelyadapter.criteria=prog;
                SharedPreferences.Editor editorminor = getSharedPreferences("Criteria", MODE_PRIVATE).edit();
                editorminor.putInt("percentage",prog);
                editorminor.apply();
                Intent mynewintenr=new Intent(getApplicationContext(),homenavi.class);
                finish();
                startActivity(mynewintenr);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });

    }


}
