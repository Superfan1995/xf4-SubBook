package com.example.xf4_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * EditSubActivity
 *
 * change an exsiting subscription
 *
 * @author xf4
 * @version 1.0
 */

public class EditSubActivity extends AppCompatActivity {

    /** called when the activity is first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub);

        final int subsPosition;     // position of the subscription in arraylist
        final String subsName;      // name of subscription
        final String subsDate;      // date of subscription
        final String subsComment;   // comment of subscription
        final int intCharge;        // charge of subscription

        String subsCharge;  // Charge in string
        String subsYear;    // year of date in string
        String subsMonth;   // month of date in string
        String subsDay;     // day of date in string

        final EditText editName = (EditText) findViewById(R.id.editName2);
        final EditText editYear = (EditText) findViewById(R.id.editYear2);
        final EditText editMonth = (EditText) findViewById(R.id.editMonth2);
        final EditText editDay = (EditText) findViewById(R.id.editDay2);
        final EditText editCharge = (EditText) findViewById(R.id.editCharge2);
        final EditText editComment = (EditText) findViewById(R.id.editComment2);

        Button returnButton = findViewById(R.id.editReturnButton);  // return without change button
        Button saveButton = findViewById(R.id.editSaveButton);  // use the change button

        // receive data of subscription
        Bundle extras = getIntent().getExtras();

        subsPosition = extras.getInt("position");
        subsName = extras.getString("name");
        subsDate = extras.getString("date");
        subsCharge = extras.getString("charge");
        subsComment = extras.getString("comment");

        // convert charge to int
        intCharge = Integer.parseInt(subsCharge);

        // extract year, month, day from date
        subsYear = subsDate.substring(0, 4);
        subsMonth = subsDate.substring(5, 7);
        subsDay = subsDate.substring(8, 10);

        // set the original value of the subscription
        editName.setText(subsName);
        editYear.setText(subsYear);
        editMonth.setText(subsMonth);
        editDay.setText(subsDay);
        editCharge.setText(subsCharge);
        editComment.setText(subsComment);

        /**
         * when the save change button is clicked
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int intMonth = 0;       // new month in int
                int intDay = 0;         // new day in int
                int resultMode = 2;     // return mode is 2 for change exsiting subscription
                String warning;         // print error message is input not legal

                // get the input data
                String newName = editName.getText().toString();
                String newYear = editYear.getText().toString();
                String newMonth = editMonth.getText().toString();
                String newDay = editDay.getText().toString();
                String chargeString = editCharge.getText().toString();
                String newComment = editComment.getText().toString();

                // place to show error message
                TextView resultView = findViewById(R.id.viewResult2);

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
                            Intent intent = new Intent(EditSubActivity.this,
                                    MainActivity.class);

                            String newDate = newYear + "-" + newMonth + "-" + newDay;

                            intent.putExtra("mode", resultMode);
                            intent.putExtra("position", subsPosition);
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
         * return to ViewSubActivity(view the subscription detail) without change
         */
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // return all data without change
                Intent intent = new Intent(EditSubActivity.this, ViewSubActivity.class);

                intent.putExtra("position", subsPosition);
                intent.putExtra("name", subsName);
                intent.putExtra("date", subsDate);
                intent.putExtra("charge", intCharge);
                intent.putExtra("comment", subsComment);

                startActivity(intent);
            }
        });

    }
}
