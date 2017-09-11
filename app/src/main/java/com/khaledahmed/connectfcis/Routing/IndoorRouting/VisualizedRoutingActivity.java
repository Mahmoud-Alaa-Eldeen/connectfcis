package com.khaledahmed.connectfcis.Routing.IndoorRouting;
/*
*get the src & dest rooms selected
*send to DrawAndShowVisualized_Path to get path and draw in college map image.
*/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.khaledahmed.connectfcis.R;

import java.util.ArrayList;
import java.util.List;

public class VisualizedRoutingActivity extends AppCompatActivity {

    private Spinner source_spinner;
    private Spinner destination_spinner;
    private Button navigate_button;
    private Intent intent;
    private List<String> nodes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualized_routing);


        source_spinner = (Spinner) findViewById(R.id.source_spinner2);
        destination_spinner = (Spinner) findViewById(R.id.destination_spinner2);
        navigate_button = (Button) findViewById(R.id.navigate2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.RoomsForVisualzed));
        source_spinner.setAdapter(adapter);
        destination_spinner.setAdapter(adapter);

        navigate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String START = source_spinner.getSelectedItem().toString();
                String END = destination_spinner.getSelectedItem().toString();


                intent = new Intent(getApplicationContext(), DrawAndShowVisualized_Path.class);
                intent.removeExtra("srcAndDest");
                intent.putExtra("srcAndDest", START + "," + END);
                startActivity(intent);


            }
        });


    }
}