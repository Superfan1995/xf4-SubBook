package com.example.xf4_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NewSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sub);

        Button CommitButton = (Button) findViewById(R.id.button_commit);
        Button QuitButton = (Button) findViewById(R.id.button_quit);

        CommitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {

                String warning;
                TextView resultView = findViewById(R.id.View_result);

                EditText nameText = (EditText) findViewById(R.id.Edit_name);
                EditText dateText = (EditText) findViewById(R.id.Edit_date);
                EditText chargeText = (EditText) findViewById(R.id.Edit_charge);
                EditText commentText = (EditText) findViewById(R.id.Edit_comment);

                String newName = nameText.getText().toString();
                String dateString = dateText.getText().toString();
                String chargeString = chargeText.getText().toString();
                String newComment = commentText.getText().toString();

                int resultMode = 1;

                if ( (!newName.matches("")) &&
                        (!dateString.matches("")) &&
                        (!chargeString.matches("")) ) {

                    int newCharge = Integer.parseInt(chargeString);

                    // https://stackoverflow.com/questions/6510724/how-to-convert-java-string-to-date-object
                    // 2018-1-31
                    try {
                        Date newDate;
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        newDate = df.parse(dateString);

                        Intent intent = new Intent(NewSubActivity.this,
                                MainActivity.class);

                        intent.putExtra("name", newName);
                        intent.putExtra("date", dateString);
                        intent.putExtra("charge", chargeString);
                        intent.putExtra("comment", newComment);

                        startActivity(intent);

                    } catch (ParseException e) {
                        e.printStackTrace();

                        warning = "Error: Date format is not correct!";
                        resultView.setText(warning);
                    }

                }

                else {

                    if (newName.matches("")) {
                        warning = "Error: New subscription required a name!";
                    }
                    else if (dateString.matches("")) {
                        warning = "Error: New subscription required a date started!";
                    }
                    else {
                        warning = "Error: New subscription required a monthly charge!";
                    }

                    resultView.setText(warning);
                }

            }
        });

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
