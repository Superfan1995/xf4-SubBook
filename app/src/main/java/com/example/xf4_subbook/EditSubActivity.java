package com.example.xf4_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub);

        final int subsPosition;
        final String subsName;
        final String subsDate;
        final String subsComment;
        final int intCharge;

        String subsCharge;
        String subsYear;
        String subsMonth;
        String subsDay;

        final EditText editName = (EditText) findViewById(R.id.editName2);
        final EditText editYear = (EditText) findViewById(R.id.editYear2);
        final EditText editMonth = (EditText) findViewById(R.id.editMonth2);
        final EditText editDay = (EditText) findViewById(R.id.editDay2);
        final EditText editCharge = (EditText) findViewById(R.id.editCharge2);
        final EditText editComment = (EditText) findViewById(R.id.editComment2);

        Button returnButton = findViewById(R.id.editReturnButton);
        Button saveButton = findViewById(R.id.editSaveButton);

        Bundle extras = getIntent().getExtras();

        subsPosition = extras.getInt("position");
        subsName = extras.getString("name");
        subsDate = extras.getString("date");
        subsCharge = extras.getString("charge");
        subsComment = extras.getString("comment");

        intCharge = Integer.parseInt(subsCharge);

        subsYear = subsDate.substring(0, 4);
        subsMonth = subsDate.substring(5, 7);
        subsDay = subsDate.substring(8, 10);

        editName.setText(subsName);
        editYear.setText(subsYear);
        editMonth.setText(subsMonth);
        editDay.setText(subsDay);
        editCharge.setText(subsCharge);
        editComment.setText(subsComment);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int intMonth = 0;
                int intDay = 0;
                int resultMode = 2;

                String warning;
                String newName = editName.getText().toString();
                String newYear = editYear.getText().toString();
                String newMonth = editMonth.getText().toString();
                String newDay = editDay.getText().toString();
                String chargeString = editCharge.getText().toString();
                String newComment = editComment.getText().toString();

                TextView resultView = findViewById(R.id.viewResult2);

                if (!newName.matches("") &&
                        (newYear.length() == 4 ) &&
                        (newMonth.length() == 2) &&
                        (newDay.length() == 2) &&
                        (!chargeString.matches(""))) {

                    intMonth = Integer.parseInt(newMonth);
                    intDay = Integer.parseInt(newDay);

                    if ( (intMonth > 0) && (intMonth < 13) ) {

                        if ( (intDay > 0) && (intDay <32) ) {

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

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
