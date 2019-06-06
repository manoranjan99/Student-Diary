package com.manoranjank.studentdiary;

/**
 * Created by Manoranjan K on 03-06-2019.
 */

public class attendance {

    private String subjectname;
    private int attended;
    private int bunked;

    public attendance(String subjectname,int attended,int bunked)
    {
        this.subjectname=subjectname;
        this.attended=attended;
        this.bunked=bunked;
    }





    public String getSubjectname() {
        return subjectname;
    }

    public int getAttended() {
        return attended;
    }

    public int getBunked() {
        return bunked;
    }
}
