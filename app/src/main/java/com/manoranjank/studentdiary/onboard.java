package com.manoranjank.studentdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

public class onboard extends AppCompatActivity {

    TextInputEditText Rollnumber;
    Button mRollbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
Rollnumber=(TextInputEditText)findViewById(R.id.addRollNumbertext);
mRollbutton=(Button) findViewById(R.id.addRollNumber);

mRollbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String value=Rollnumber.getText().toString();
        if(TextUtils.isEmpty(value)) {
            Rollnumber.setError("Textfield should not be empty");
            return;
        }
        else {

            SharedPreferences.Editor editorminor = getSharedPreferences("rollnumber", MODE_PRIVATE).edit();
            editorminor.putString("valueofroll",value);
            editorminor.apply();
            Intent nextintent=new Intent(getApplicationContext(),MainActivity.class);
            finish();
            startActivity(nextintent);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        }
    }
});







    }

    @Override
    public void onBackPressed() {}
}
