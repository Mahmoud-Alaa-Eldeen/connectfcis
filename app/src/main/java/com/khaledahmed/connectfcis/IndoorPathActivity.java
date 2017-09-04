package com.khaledahmed.connectfcis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IndoorPathActivity extends AppCompatActivity {

    private TextView pathText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_path);
        pathText = (TextView) findViewById(R.id.textView5);
        pathText.setText("");
        pathText.setText(getIntent().getExtras().getString("path"));
    }
}
