package com.khaledahmed.connectfcis.Structure;

/**
 * Created by Khaled Ahmed on 2/10/2017.
 */
public class Staff extends User {
    private String departmentId;
    private String subjectId;
    private String type;

    public Staff() {

    }

    public Staff(String departmentId, String subjectId, String type) {
        this.departmentId = departmentId;
        this.subjectId = subjectId;
        this.type = type;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
