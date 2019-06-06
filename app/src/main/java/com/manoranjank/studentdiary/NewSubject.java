package com.manoranjank.studentdiary;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.antonious.materialdaypicker.MaterialDayPicker;

public class NewSubject extends AppCompatActivity {

    @BindView(R.id.action_bar)
    Toolbar action_bar;
    @BindView(R.id.dayPicker)
    MaterialDayPicker dayPicker;
    @BindView(R.id.inputLayout)
    TextInputLayout inputLayout;
    @BindView(R.id.editText)
    TextInputEditText editText;
    @BindView(R.id.timeSelect)
    TextInputEditText timeSelect;

    public String subject_name;
    public String time;
    public List<MaterialDayPicker.Weekday> dayPickerList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_subject);
        ButterKnife.bind(this);
        setSupportActionBar(action_bar);

        timeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });

    }

    public void showTimeDialog() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(NewSubject.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeSelect.setText(getTime(hourOfDay, minute));

            }
        }, 8, 30, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_timetable_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {

            subject_name = Objects.requireNonNull(editText.getText()).toString();
            time = Objects.requireNonNull(timeSelect.getText()).toString();
            dayPickerList = dayPicker.getSelectedDays(); //list of selected days;

            if (subject_name.equals("") || dayPickerList.size() == 0) {
                if (subject_name.equals("")) {
                    inputLayout.setError("Subject name can't be empty");
                } else {
                    Toast.makeText(this, "Pick a day!",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                HomeActivity.listPopulateHandle.clickHandle(subject_name, time, dayPickerList);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getTime(int hr, int min) {
        Time tme = new Time(hr, min, 0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }

}
