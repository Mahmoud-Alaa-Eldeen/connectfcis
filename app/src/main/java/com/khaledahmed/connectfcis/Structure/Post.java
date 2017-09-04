package com.khaledahmed.connectfcis.Structure;

/**
 * Created by Khaled Ahmed on 2/10/2017.
 */
public class Post {

    private String postId;
    private String postContent;
    private String creationDateAndTime;
    private String userId;
    private String groupId;

    public Post() {

    }

    public Post(String postId, String postContent, String creationDateAndTime, String userId) {
        this.postId = postId;
        this.postContent = postContent;
        this.creationDateAndTime = creationDateAndTime;
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getCreationDateAndTime() {
        return creationDateAndTime;
    }

    public void setCreationDateAndTime(String creationDateAndTime) {
        this.creationDateAndTime = creationDateAndTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
