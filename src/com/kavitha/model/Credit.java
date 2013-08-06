package com.kavitha.model;

public class Credit {
    private String debitedFrom;
    private String creditedTo;
    private long amount;

    public String getdebitedFrom() {
        return debitedFrom;
    }

    public void setdebitedFrom(String debitedFrom) {
        this.debitedFrom = debitedFrom;
    }

    public String getcreditedTo() {
        return creditedTo;
    }

    public void setcreditedTo(String creditedTo) {
        this.creditedTo = creditedTo;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}