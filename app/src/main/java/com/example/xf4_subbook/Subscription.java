package com.example.xf4_subbook;

import java.util.Date;

/**
 * Created by superfan1995 on 18/1/29.
 */

public class Subscription {

    private String name;
    private String date;
    private int charge;
    private String comment;

    public Subscription(String name, String date, int charge, String comment) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    // From http://www.ezzylearning.com/tutorial/binding-android-listview-with-custom-objects-using-arrayadapter
    // 2018-2-2
    @Override
    public String toString() {
        return this.name + " | " + this.date + " | $" + this.charge;
    }

}
