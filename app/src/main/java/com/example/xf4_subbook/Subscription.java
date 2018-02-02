package com.example.xf4_subbook;

import java.util.Date;

/**
 * Created by superfan1995 on 18/1/29.
 */

public class Subscription {

    private String name;
    private Date date;
    private int charge;
    private String comment;

    public Subscription(String name, Date date, int charge, String comment) {
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NameTooLongException {
        if (name.length() < 21) {
            this.name = name;
        }
        else {
            throw new NameTooLongException();
        }
    }

    public Date getDate() {
        return date;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) throws ChargeNegativeException {
        if (charge >= 0 ) {
            this.charge = charge;
        }
        else {
            throw new ChargeNegativeException();
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) throws CommentTooLongException {
        if (comment.length() < 31) {
            this.comment = comment;
        }
        else {
            throw new CommentTooLongException();
        }
    }

    public String toString() {
        return name;
    }

}
