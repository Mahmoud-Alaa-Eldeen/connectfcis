package com.khaledahmed.connectfcis.Utilities;

/**
 * Created by Khaled Ahmed on 2/24/2017.
 */
public class GroupDataProvider {

    private byte [] groupPhoto;
    private String groupName;
    private String groupDescription;
    private int group_Id;

    public GroupDataProvider(byte[] groupPhoto, String groupName, String groupDescription, int group_Id) {
        this.groupPhoto = groupPhoto;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.group_Id = group_Id;
    }

    public byte[] getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(byte[] groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public int getGroup_Id() {
        return group_Id;
    }

    public void setGroup_Id(int group_Id) {
        this.group_Id = group_Id;
    }
}
