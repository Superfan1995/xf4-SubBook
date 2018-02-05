package com.example.xf4_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * ViewSubActivity
 *
 * Control the activity of show user a single subscription in recorded subscription list
 * Can return to main activity, edit the subscription or delete the subscription
 *
 * @author xf4
 * @version 1.0
 */

public class ViewSubActivity extends AppCompatActivity {

    /**
     * Called when the ViewSubActivity is first created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sub);

        final int subsPosition;     // position of subscription in arraylist
        final String subsName;      // name of the subscription
        final String subsDate;      // date started of subscription
        final String subsCharge;    // monthly charge of subscription
        final String subsComment;   // comment of subscription
        String showCharge;          // monthly charge in String

        TextView viewName;          // TextView of name
        TextView viewDate;          // TextView of date
        TextView viewCharge;        // TextView of charge
        TextView viewComment;       // TextView of comment

        // get data from MainActivity or EditSubActivity
        Bundle extras = getIntent().getExtras();

        subsPosition = extras.getInt("position");
        subsName = extras.getString("name");
        subsDate = extras.getString("date");
        subsCharge = "" + extras.getInt("charge");
        subsComment = extras.getString("comment");
        showCharge = "$" + subsCharge;

        viewName = (TextView) findViewById(R.id.showSubsName);
        viewDate = (TextView) findViewById(R.id.showSubsDate);
        viewCharge = (TextView) findViewById(R.id.showSubsCharge);
        viewComment = (TextView) findViewById(R.id.showSubsComment);

        viewName.setText(subsName);
        viewDate.setText(subsDate);
        viewCharge.setText(showCharge);
        viewComment.setText(subsComment);

        Button returnButton = (Button) findViewById(R.id.returnButton); // return button
        Button editButton = (Button) findViewById(R.id.editButton) ; // edit button
        Button deleteButton = (Button) findViewById(R.id.deleteButton); // delete button

        /**
         * Return to MainActivity when return button is clicked
         */
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewSubActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Enter EditSubActivity when edit button is clicked
         */
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewSubActivity.this,
                        EditSubActivity.class);

                intent.putExtra("position", subsPosition);
                intent.putExtra("name", subsName);
                intent.putExtra("date", subsDate);
                intent.putExtra("charge", subsCharge);
                intent.putExtra("comment", subsComment);

                startActivity(intent);
            }
        });

        /**
         * Detele the current viewing subscription when delete button is clicked
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewSubActivity.this,
                        MainActivity.class);

                intent.putExtra("mode", 3);
                intent.putExtra("position", subsPosition);

                startActivity(intent);
            }
        });

    }
}
