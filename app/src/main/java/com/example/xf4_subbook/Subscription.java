package com.example.xf4_subbook;

import java.util.Date;

/**
 * Created by superfan1995 on 18/1/29.
 */

/**
 * Subscription
 *
 * Manage the subscription class
 *
 * @author xf4
 * @version 1.0
 *
 */

public class Subscription {

    private String name;    // name of subscription (upto 20 char)
    private String date;    // date started of subscription (yyy-MM-dd)
    private int charge;     // monthly charge of subscription, non-negative, canadian dollars
    private String comment; // comment of subscription (upto 30 char)

    /**
     * Construct a Subscription instance
     *
     * @param name name of subscription
     * @param date data started of subscription
     * @param charge monthly charge of subscription
     * @param comment comment of subscription
     */
    public Subscription(String name, String date, int charge, String comment) {
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }

    /**
     * Return name of subscription
     *
     * @return name of subscription
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of subscription to new value
     *
     * @param name new name of subscription
     * @throws NameTooLongException throws when the lenght of name is more than 20 chars
     */
    public void setName(String name) throws NameTooLongException {
        if (name.length() < 21) {
            this.name = name;
        }
        else {
            throw new NameTooLongException();
        }
    }

    /**
     * Return date of subscription
     *
     * @return date of subscription
     */
    public String getDate() {
        return date;
    }

    /**
     * Set date of subscription to new value
     *
     * @param date new date of subscription
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Return monthly charge of subscription
     *
     * @return monthly charge of subscription
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Set monthly charge of subscription to a new value
     *
     * @param charge new charge of the subscription
     * @throws ChargeNegativeException throws when charge is negetive values
     */
    public void setCharge(int charge) throws ChargeNegativeException {
        if (charge >= 0 ) {
            this.charge = charge;
        }
        else {
            throw new ChargeNegativeException();
        }
    }

    /**
     * Return comment of subscription
     *
     * @return the comment of subscription
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment of subscription to a new value
     *
     * @param comment new comment of subscription
     * @throws CommentTooLongException throws when comment is longer than 30 chars
     */
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

    /**
     * Return the basic information of subscription
     *
     * @return the message that will be showed (ex. in listview)
     */
    @Override
    public String toString() {
        return this.name + " | " + this.date + " | $" + this.charge;
    }

}
