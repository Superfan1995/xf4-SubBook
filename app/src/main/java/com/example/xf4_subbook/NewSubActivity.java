package com.example.xf4_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class NewSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sub);

        Button CommitButton = (Button) findViewById(R.id.buttonCommit);
        Button QuitButton = (Button) findViewById(R.id.buttonQuit);

        CommitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {

                String warning;
                TextView resultView = findViewById(R.id.viewResult);

                EditText nameText = (EditText) findViewById(R.id.editName);
                EditText yearText = (EditText) findViewById(R.id.editYear);
                EditText monthText = (EditText) findViewById(R.id.editMonth);
                EditText dayText = (EditText) findViewById(R.id.editDay);
                EditText chargeText = (EditText) findViewById(R.id.editCharge);
                EditText commentText = (EditText) findViewById(R.id.editComment);

                String newName = nameText.getText().toString();
                String newYear = yearText.getText().toString();
                String newMonth = monthText.getText().toString();
                String newDay = dayText.getText().toString();
                String chargeString = chargeText.getText().toString();
                String newComment = commentText.getText().toString();

                int intMonth = 0;
                int intDay = 0;
                int resultMode = 1;

                if (!newName.matches("") &&
                        (newYear.length() == 4 ) &&
                        (newMonth.length() == 2) &&
                        (newDay.length() == 2) &&
                        (!chargeString.matches(""))) {

                    intMonth = Integer.parseInt(newMonth);
                    intDay = Integer.parseInt(newDay);

                    if ( (intMonth > 0) && (intMonth < 13) ) {

                        if ( (intDay > 0) && (intDay <32) ) {

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

                        else {
                            warning = "Error: Day need to from 01 to 31";
                            resultView.setText(warning);
                        }
                    }

                    else {
                        warning = "Error: Month need to from 01 to 12";
                        resultView.setText(warning);
                    }

                }
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
