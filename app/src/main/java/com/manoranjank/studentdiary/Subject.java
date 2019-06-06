package com.manoranjank.studentdiary;

public class Subject {
    private String subjectName;
    private String time;
    private String dayOfWeek;

    public Subject(String subjectName, String time, String dayOfWeek) {
        this.subjectName = subjectName;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTime() {
        return time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}
