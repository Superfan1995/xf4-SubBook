/*
 *  Copyright  © 2018 Xiang Fan, CMPUT301, University of Alberta - All right REserved.
 *  You may use, distribute or modify this code under terms and conditions of Code of
 * Students  Behaviors at
 *  University of Alberta.
 *  You can find a cope of the license in this project. Otherwise, please contact
 * xf4@ualberta.ca
 * /
 */

package com.example.xf4_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * NewSubActivity
 *
 * create a new subscritpion
 *
 * @author xf4
 * @version 1.0
 */

public class NewSubActivity extends AppCompatActivity {

    /** called when the activity first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sub);

        Button CommitButton = (Button) findViewById(R.id.buttonCommit); // commit button
        Button QuitButton = (Button) findViewById(R.id.buttonQuit); // quit button

        /**
         * processing the input data when commit button clicked. If legal, return information
         * of new subscription
         */
        CommitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {

                String warning; // print error message if data not legal
                TextView resultView = findViewById(R.id.viewResult); // warning TextView

                EditText nameText = (EditText) findViewById(R.id.editName);
                EditText yearText = (EditText) findViewById(R.id.editYear);
                EditText monthText = (EditText) findViewById(R.id.editMonth);
                EditText dayText = (EditText) findViewById(R.id.editDay);
                EditText chargeText = (EditText) findViewById(R.id.editCharge);
                EditText commentText = (EditText) findViewById(R.id.editComment);

                // get the input data required to create a subscription
                String newName = nameText.getText().toString();
                String newYear = yearText.getText().toString();
                String newMonth = monthText.getText().toString();
                String newDay = dayText.getText().toString();
                String chargeString = chargeText.getText().toString();
                String newComment = commentText.getText().toString();

                int intMonth = 0; // month of data started in int
                int intDay = 0; // day of data started in int
                int resultMode = 1; // the return mode of subscription, 1 for creating new

                // check if the form od date is legal, and charge and name not null
                if (!newName.matches("") &&
                        (newYear.length() == 4 ) &&
                        (newMonth.length() == 2) &&
                        (newDay.length() == 2) &&
                        (!chargeString.matches(""))) {

                    intMonth = Integer.parseInt(newMonth);
                    intDay = Integer.parseInt(newDay);

                    if ( (intMonth > 0) && (intMonth < 13) ) {

                        if ( (intDay > 0) && (intDay <32) ) {

                            // send data to MainActivity to create new subscription
                            Intent intent = new Intent(NewSubActivity.this,
                                    MainActivity.class);

                            String newDate = newYear + "-" + newMonth + "-" + newDay;

                            intent.putExtra("mode", resultMode);
                            intent.putExtra("name", newName);
                            intent.putExtra("date", newDate);
                            intent.putExtra("charge", chargeString);
                            intent.putExtra("comment", newComment);

                            startActivity(intent);
                        }

                        // print warning is day is not avaliable
                        else {
                            warning = "Error: Day need to from 01 to 31";
                            resultView.setText(warning);
                        }
                    }

                    // print warning if month is not avaliable
                    else {
                        warning = "Error: Month need to from 01 to 12";
                        resultView.setText(warning);
                    }

                }

                // print warning is not all required data are provided
                else {

                    if (newName.matches("")) {
                        warning = "Error: New subscription required a name!";
                    }
                    else if (newYear.matches("") ) {
                        warning = "Error: New subscription required a Year with exactly 4 char: yyyy";
                    }
                    else if (newMonth.matches("") ) {
                        warning = "Error: New subscription required a Month with exactly 2 char: MM";
                    }
                    else if (newDay.matches("") ) {
                        warning = "Error: New subscription required a Day with exactly 2 char: dd";
                    }
                    else {
                        warning = "Error: New subscription required a monthly charge!";
                    }

                    resultView.setText(warning);
                }

            }
        });

        /**
         * return to MainActicity without creating any subscription
         */
        QuitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {
                Intent intent = new Intent(NewSubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
