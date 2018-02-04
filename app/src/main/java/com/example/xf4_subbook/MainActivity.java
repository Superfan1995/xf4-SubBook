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

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "subs_list_save";
    private ListView listViewSubs;
    private TextView viewNum;
    private TextView viewTotalCharge;

    private ArrayList<Subscription> subsList;
    private ArrayAdapter<Subscription> adapter;

    private int subsNumber;
    private int totalCharge;
    // test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int returnMode;
        String newName;
        String newDate;
        String newComment;
        String chargeString;
        int newCharge;

        Button newSubButton = (Button) findViewById(R.id.buttonCreate);
        Button clearSubButton = (Button) findViewById(R.id.buttonClear);
        listViewSubs = (ListView) findViewById(R.id.listViewSubs);

        loadFromFile();
        //subsList = new ArrayList<Subscription>();
        adapter = new ArrayAdapter<Subscription>(this,
                android.R.layout.simple_list_item_1, subsList);
        listViewSubs.setAdapter(adapter);

        showTotal();

        // From https://stackoverflow.com/questions/4233873/how-do-i-get-extra-data-from-intent-on-android
        // 2018-1-31
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            returnMode = extras.getInt("mode");

            if (returnMode == 1) {
                newName = extras.getString("name");
                newDate = extras.getString("date");
                chargeString = extras.getString("charge");
                newComment = extras.getString("comment");
                newCharge = Integer.parseInt(chargeString);

                Subscription subscription = new Subscription(newName, newDate, newCharge, newComment);

                subsList.add(subscription);
                adapter.notifyDataSetChanged();
            }

            showTotal();
            saveInFile();
        }

        listViewSubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });

        newSubButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {

                // https://developer.android.com/training/basics/firstapp/starting-activity.html
                // 2018-01-30
                Intent intent = new Intent(MainActivity.this, NewSubActivity.class);
                startActivity(intent);
            }
        });

        clearSubButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                subsList.clear();
                adapter.notifyDataSetChanged();
                saveInFile();
            }
        });

    }

    public void showTotal () {

        subsNumber = subsList.size();
        totalCharge = 0;

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

    //@Override
    //protected void onDestroy() {
    //    super.onDestroy();
    //}

}