package com.example.xf4_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // lonely twitter From lab 3
    // https://github.com/Superfan1995/lonelyTwitter/tree/lab3
    // 2018-01-30
    private static final String FILENAME = "Subscription.sav";
    private ListView oldSubList;

    private ArrayList<Subscription> subscriptionList;
    private ArrayAdapter<Subscription> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String newName;
        Date newDate;
        int newCharge;
        String newComment;

        String dateString;
        String chargeString;

        Button newSubButton = (Button) findViewById(R.id.button3);
        oldSubList = (ListView) findViewById(R.id.listView);

        // From https://stackoverflow.com/questions/4233873/how-do-i-get-extra-data-from-intent-on-android
        // 2018-1-31
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            newName = extras.getString("name");
            dateString = extras.getString("date");
            chargeString = extras.getString("charge");
            newComment = extras.getString("comment");

            newCharge = Integer.parseInt(chargeString);

            // From https://stackoverflow.com/questions/6510724/how-to-convert-java-string-to-date-object
            // 2018-1-31
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                newDate = df.parse(dateString);

                Subscription subscription =
                        new Subscription(newName, newDate, newCharge, newComment);

                subscriptionList.add(subscription);

                adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptionList); // test

                oldSubList.setAdapter(adapter); // test

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        newSubButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {

                // https://developer.android.com/training/basics/firstapp/starting-activity.html
                // 2018-01-30
                Intent intent = new Intent(MainActivity.this, NewSubActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadFromFile() {

    }

    // From lab lonelyTwitter: https://github.com/Superfan1995/lonelyTwitter/tree/lab4
    // 2018-01-31
    private void saveInFile() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}