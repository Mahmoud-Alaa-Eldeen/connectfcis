package com.khaledahmed.connectfcis.Routing.IndoorRouting;
/*
*get the src & dest rooms user selected
*send to DrawAndShowVisualized_PathActivity to get path and draw on college map image.
*/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.khaledahmed.connectfcis.R;

public class VisualizedRoutingActivity extends AppCompatActivity {

    private Spinner source_spinner;
    private Spinner destination_spinner;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualized_routing);


        source_spinner = (Spinner) findViewById(R.id.source_spinner2);
        destination_spinner = (Spinner) findViewById(R.id.destination_spinner2);
        Button showPath = (Button) findViewById(R.id.navigate2);

        //configure adapter for src & dest spinner
        //get array from xml for first floor rooms
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.RoomsForVisualzed));
        source_spinner.setAdapter(adapter);
        destination_spinner.setAdapter(adapter);

        showPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String srcRoom = source_spinner.getSelectedItem().toString();
                String destRoom = destination_spinner.getSelectedItem().toString();


                // send src & dest room to DrawAndShowVisualized_PathActivity
                intent = new Intent(getApplicationContext(), DrawAndShowVisualized_PathActivity.class);
                intent.removeExtra("srcAndDest");
                intent.putExtra("srcAndDest", srcRoom + "," + destRoom);
                startActivity(intent);


            }
        });


    }
}
