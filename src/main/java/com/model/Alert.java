package com.model;

import java.io.Serializable;

public class Alert implements Serializable {

    private Integer alertID;
    private Integer messageCode;
    private Integer itemID;
    private Integer userID;

    public Alert() {
    }

    public Alert(Integer alertID, Integer messageCode, Integer itemID, Integer userID) {
        this.itemID = itemID;
        this.userID = userID;
        this.alertID = alertID;
        this.messageCode = messageCode;
    }

    public void setItemID(Integer i) {
        this.itemID = i;
    }

    public int getItemID() {
        return itemID;
    }

    public void setUserID(Integer i) {
        this.userID = i;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setAlertID(Integer i) {
        this.alertID = i;
    }

    public Integer getAlertID() {
        return alertID;
    }

    public void setMessageCode(Integer i) {
        this.messageCode = i;
    }

    public Integer getMessageCode() {
        return messageCode;
    }
}