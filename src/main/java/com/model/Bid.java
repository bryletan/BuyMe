package com.model;

import java.io.Serializable;

public class Bid implements Serializable {

    private Integer bidID;
    private Integer itemID;
    private Double bidPrice;
    private Double upperLimit;
    private Double bidIncrement;
    private Integer userID;
    private Boolean hasAlert;
    private Integer auctionID;

    public Bid() {
    }

    public Bid(Integer bidID, Integer itemID, Double bidPrice, Double upperLimit, Double bidIncrement, Integer userID, Boolean hasAlert,
            Integer auctionID) {
        this.bidID = bidID;
        this.itemID = itemID;
        this.bidPrice = bidPrice;
        this.upperLimit = upperLimit;
        this.bidIncrement = bidIncrement;
        this.userID = userID;
        this.auctionID = auctionID;
    }

    public void setBidID(Integer i) {
        this.bidID = i;
    }

    public int getBidID() {
        return bidID;
    }

    public void setItemID(Integer i) {
        this.itemID = i;
    }

    public int getItemID() {
        return itemID;
    }

    public void setBidPrice(Double p) {
        this.bidPrice = p;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setUpperLimit(Double p) {
        this.upperLimit = p;
    }

    public Double getUpperLimit() {
        return upperLimit;
    }

    public void setBidIncrement(Double p) {
        this.bidIncrement = p;
    }

    public Double getBidIncrement() {
        return bidIncrement;
    }

    public void setUserID(Integer i) {
        this.userID = i;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setHasAlert(Boolean b) {
        this.hasAlert = b;
    }

    public Boolean getHasAlert() {
        return hasAlert;
    }
    
    public void setAuctionID(Integer i) {
        this.auctionID = i;
    }

    public Integer getAuctionID() {
        return auctionID;
    }
}