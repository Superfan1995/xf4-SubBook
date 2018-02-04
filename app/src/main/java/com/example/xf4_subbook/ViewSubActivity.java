package com.example.xf4_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sub);

        final int subsPosition;
        final String subsName;
        final String subsDate;
        final String subsCharge;
        final String subsComment;
        String showCharge;

        TextView viewName;
        TextView viewDate;
        TextView viewCharge;
        TextView viewComment;

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

        Button returnButton = (Button) findViewById(R.id.returnButton);
        Button editButton = (Button) findViewById(R.id.editButton) ;

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewSubActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });

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
    }
}
