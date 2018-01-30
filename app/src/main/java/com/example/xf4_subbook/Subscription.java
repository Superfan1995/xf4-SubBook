package com.example.xf4_subbook;

import java.util.Date;

/**
 * Created by superfan1995 on 18/1/29.
 */

public class Subscription {

    private String name;
    private Date date;
    private Float charge;
    private String comment;

    public Subscription(String name, Date date, Float charge, String comment) {
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }
}
