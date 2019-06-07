package com.manoranjank.studentdiary;

public interface FragmentListener {
    void populateList(String subjectName, String time, String day);
    void updateList(int i);
    String getFragmentName();
}