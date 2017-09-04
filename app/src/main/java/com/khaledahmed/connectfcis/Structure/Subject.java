package com.khaledahmed.connectfcis.Structure;

/**
 * Created by Khaled Ahmed on 2/10/2017.
 */
public class Subject {
    private String subjectId;
    private String subjectName;
    private String Department;
    private String staffMemberId;
    private int academicYear;

    public Subject() {

    }

    public Subject(String subjectId, String subjectName, String department, String staffMemberId, int academicYear) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        Department = department;
        this.staffMemberId = staffMemberId;
        this.academicYear = academicYear;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getStaffMemberId() {
        return staffMemberId;
    }

    public void setStaffMemberId(String staffMemberId) {
        this.staffMemberId = staffMemberId;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }
}
