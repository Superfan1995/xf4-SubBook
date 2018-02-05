package com.example.xf4_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * MainActivity
 *
 * Maintain and show a list of subscriptions and summery information. provide option to
 * view detail of a single subscription, create new subscription and delete all subscription
 *
 * @author xf4
 * @version 1.0
 *
 */

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "subs_list_save";    // the save file
    private ListView listViewSubs;      //  subscription ListView
    private TextView viewNum;           // number of subscription TextView
    private TextView viewTotalCharge;   // Total monthly charge of all subscription TextView

    private ArrayList<Subscription> subsList;       // subscription list
    private ArrayAdapter<Subscription> adapter;     // subscription list adapter

    private int subsNumber;     // number of subscription
    private int totalCharge;    // total monthly charge of all subscription

    /** Called when the MainActivity is first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int returnMode;         // which activity send the data
        int returnPosition;     // position of subscription in list that changed

        String newName;         // subscription name
        String newDate;         // subscription date
        String newComment;      // subscription comment
        String chargeString;    // subscription charge in string
        final int newCharge;    // subscription charge in int

        Button newSubButton = (Button) findViewById(R.id.buttonCreate);     // create button
        Button clearSubButton = (Button) findViewById(R.id.buttonClear);    // clear button
        listViewSubs = (ListView) findViewById(R.id.listViewSubs);

        // load the subscriptions in arraylist, show in listview and set adapter
        loadFromFile();
        adapter = new ArrayAdapter<Subscription>(this,
                android.R.layout.simple_list_item_1, subsList);
        listViewSubs.setAdapter(adapter);

        // print number and total charge of all subscriptions
        showTotal();

        // From https://stackoverflow.com/questions/4233873/how-do-i-get-extra-data-from-intent-on-android
        // 2018-1-31
        // check if new data arrive from other activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            // beginning procressing input data
            returnMode = extras.getInt("mode");

            // if mode 1, then generate a new subscription
            if (returnMode == 1) {
                newName = extras.getString("name");
                newDate = extras.getString("date");
                chargeString = extras.getString("charge");
                newComment = extras.getString("comment");
                newCharge = Integer.parseInt(chargeString);     // get charge in int

                // creating new subscription
                Subscription subscription = new Subscription(
                        newName, newDate, newCharge, newComment);

                // add to the arraylist and show the change in listview
                subsList.add(subscription);
                adapter.notifyDataSetChanged();
            }

            // if mode 2, then change an existing subscription
            else if (returnMode == 2) {

                returnPosition = extras.getInt("position");
                newName = extras.getString("name");
                newDate = extras.getString("date");
                chargeString = extras.getString("charge");
                newComment = extras.getString("comment");
                newCharge = Integer.parseInt(chargeString);     // get charge in int

                subsList.get(returnPosition).setDate(newDate);  // set to new date

                try {
                    subsList.get(returnPosition).setName(newName); // set to new name
                }
                catch (NameTooLongException e) {
                }

                try {
                    subsList.get(returnPosition).setCharge(newCharge); // set to new charge
                }
                catch (ChargeNegativeException e) {
                }

                try {
                    subsList.get(returnPosition).setComment(newComment); // set to new comment
                }
                catch (CommentTooLongException e) {
                }
            }

            // if mode 3, delete an exsiting subscription
            else if (returnMode == 3) {

                returnPosition = extras.getInt("position");
                subsList.remove(returnPosition);
            }

            showTotal();
            saveInFile();
        }

        /**
         * View detail of a subscription when being clicked in list view
         **/
        listViewSubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = subsList.get(i).getName(); // name of clicked subscription
                String date = subsList.get(i).getDate(); // date of clicked subscription
                int charge = subsList.get(i).getCharge(); // monthly charge of clicked subscription
                String comment = subsList.get(i).getComment(); // comment of clicked subscription

                // create new intent and send data to the ViewSubActivity
                Intent intent = new Intent(MainActivity.this, ViewSubActivity.class);

                intent.putExtra("position", i);
                intent.putExtra("name", name);
                intent.putExtra("date", date);
                intent.putExtra("charge", charge);
                intent.putExtra("comment", comment);

                startActivity(intent);
            }
        });

        /**
         * Create a new subscription when create new button clicked
         */
        newSubButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {

                // https://developer.android.com/training/basics/firstapp/starting-activity.html
                // 2018-01-30
                Intent intent = new Intent(MainActivity.this, NewSubActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Clear all subscriptions when clear button clicked
         */
        clearSubButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                subsList.clear();
                adapter.notifyDataSetChanged();
                saveInFile();
            }
        });

    }

    /**
     * show total number of subscriptions and total monthly charge
     */
    public void showTotal () {

        subsNumber = subsList.size();   // get the number of subscription
        totalCharge = 0;

        // add monthly charge of all subscription
        for (int i = 0; i < subsList.size(); i = i + 1) {

            totalCharge = totalCharge + subsList.get(i).getCharge();
        }

        viewNum = (TextView) findViewById(R.id.textNumSubs);
        viewTotalCharge =(TextView) findViewById(R.id.textTotalCharge);

        viewNum.setText("Number of subscriptions:  " + subsNumber);
        viewTotalCharge.setText("Total monthly charge:  $" + totalCharge);

    }

    // From lab4 lonelyTweeter: https://github.com/Superfan1995/lonelyTwitter
    // 2018-2-3
    /**
     * load the subscriptions from the save file
     */
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subsList = gson.fromJson(in, listType);
        }
        catch (FileNotFoundException e) {
            subsList = new ArrayList<Subscription>();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

    // From lab4 lonelyTweeter: https://github.com/Superfan1995/lonelyTwitter
    // 2018-2-3
    /**
     * save the new arrayList of subscriptions in save file
     */
    private void saveInFile () {

        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subsList, out);
            out.flush();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

}