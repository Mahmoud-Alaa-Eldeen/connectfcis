package com.khaledahmed.connectfcis.Structure;

/**
 * Created by Khaled Ahmed on 2/10/2017.
 */
public class Comment {
    private String commentId;
    private String commentContent;
    private String creationDateAndTime;
    private String userId;
    private String postId;

    public Comment(String commentId, String commentContent, String creationDateAndTime, String userId, String postId) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.creationDateAndTime = creationDateAndTime;
        this.userId = userId;
        this.postId = postId;
    }

    public Comment() {

    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
