package com.khaledahmed.connectfcis.Structure;

/**
 * Created by Khaled Ahmed on 2/10/2017.
 */
public class Student extends User {
    private int academicYear;

    public Student() {
    }

    public Student(int academicYear) {
        this.academicYear = academicYear;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }
}
