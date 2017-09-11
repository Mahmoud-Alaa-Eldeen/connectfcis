package com.khaledahmed.connectfcis.Routing.IndoorRouting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.khaledahmed.connectfcis.R;

public class ShowIndoorPathActivity extends AppCompatActivity {

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
