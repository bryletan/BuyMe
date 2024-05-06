package com.model;

import java.io.Serializable;

public class Question implements Serializable {

    private Integer questionID;
    private Integer askerID;
    private Integer repID;
    private String message;
    private String reply;

    public Question() {
    }

    public Question(Integer questionID, Integer askerID, Integer repID, String message, String reply) {
        this.questionID = questionID;
        this.askerID = askerID;
        this.repID = repID;
        this.message = message;
        this.reply = reply;
    }

    public String isClaimed() {
        if (repID == 0)
            return "Unclaimed";
        else
            return "Claimed by Representative " + repID;
    }

    public String getReplyDisplay() {
        if (reply == null)
            return "No reply yet.";
        else
            return reply;
    }

    public boolean isResolved() {
        if (reply == null || reply.equals(""))
            return false;
        else
            return true;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setAskerID(Integer askerID) {
        this.askerID = askerID;
    }

    public Integer getAskerID() {
        return askerID;
    }

    public void setRepID(Integer repID) {
        this.repID = repID;
    }

    public Integer getRepID() {
        return repID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }

}