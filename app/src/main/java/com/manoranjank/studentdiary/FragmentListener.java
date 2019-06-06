package com.manoranjank.studentdiary;

public interface FragmentListener {
    void populateList(String subjectName, String time, String day);
    String getFragmentName();
}
