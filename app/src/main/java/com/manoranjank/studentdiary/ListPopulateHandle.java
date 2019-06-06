package com.manoranjank.studentdiary;

import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public interface ListPopulateHandle {
    void clickHandle(String sub_name, String time, List<MaterialDayPicker.Weekday> weekdays);
}
