package com.model;

import java.time.LocalDateTime;

import java.io.Serializable;

public class Auction implements Serializable {

    private Integer auctionID;
    private Integer itemID;
    private LocalDateTime closingTime;
    private Double minPirce;
    private Integer userID;
    private Boolean isClosed;

    public Auction() {
    }

    public Auction(Integer auctionID, Integer itemID, LocalDateTime closingTime, Double minPirce, Integer userID, Boolean isClosed) {
        this.auctionID = auctionID;
        this.itemID = itemID;
        this.closingTime = closingTime;
        this.minPirce = minPirce;
        this.userID = userID;
        this.isClosed = isClosed;
    }

    public void setAuctionID(Integer i) {
        this.auctionID = i;
    }

    public int getAuctionID() {
        return auctionID;
    }

    public void setItemID(Integer i) {
        this.itemID = i;
    }

    public int getItemID() {
        return itemID;
    }

    public void setClosingTime(LocalDateTime i) {
        this.closingTime = i;
    }

    public LocalDateTime getClosingTime() {
        return closingTime;
    }

    public void setMinPrice(Double i) {
        this.minPirce = i;
    }

    public Double getMinPrice() {
        return minPirce;
    }

    public void setUserID(Integer i) {
        this.userID = i;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setIsClosed(Boolean b) {
        this.isClosed = b;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

}