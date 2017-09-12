package com.khaledahmed.connectfcis.Routing.IndoorRouting;
/*
*get the src & dest rooms user selected
*and send to Graph Algorithm, get path
*send path to ShowIndoorPathActivity to show text path.
*/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.khaledahmed.connectfcis.R;
import com.khaledahmed.connectfcis.Routing.IndoorRouting.IndoorRoutingAlgorithm.Graph;

import java.util.ArrayList;
import java.util.List;


public class TextualRoutingActivity extends AppCompatActivity {
    private Spinner sourceRooms_spinner;
    private Spinner destinationRooms_spinner;

    private Intent intent;
    private List<String> nodes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing);

        Button ShowPath_button;
        TextView tryVisualized_button;


        sourceRooms_spinner = (Spinner) findViewById(R.id.source_spinner);
        destinationRooms_spinner = (Spinner) findViewById(R.id.destination_spinner);
        ShowPath_button = (Button) findViewById(R.id.navigate);
        tryVisualized_button = (TextView) findViewById(R.id.tryVisualized);

        // build Edges Array bet. all rooms in college building
        buildEdgesBetRooms();

        // configure adapter for src & dest rooms spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.nodesY));
        sourceRooms_spinner.setAdapter(adapter);
        destinationRooms_spinner.setAdapter(adapter);

        ShowPath_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create Graph instance with array of edges bet. rooms
                Graph g = new Graph(edgesArray);

                
                String destinationRoom = destinationRooms_spinner.getSelectedItem().toString();
                String src_Room;
                String dest_Room;
                int srcSelecIndex = sourceRooms_spinner.getSelectedItemPosition();
                int destSelecIndex = destinationRooms_spinner.getSelectedItemPosition();

                //get all names of rooms from xml array
                String[] namesOfRooms = getResources().getStringArray(R.array.nodesX);


                // get names of src & dest rooms
                src_Room = namesOfRooms[srcSelecIndex];
                dest_Room = namesOfRooms[destSelecIndex];


                g.dijkstra(src_Room);// set src room to algorithm
                g.findPath(dest_Room);// set dest room, find path
                //g.printAllPaths(); //to print all paths not only the shortest one.

                // if src & dest rooms are the same
                if (src_Room == dest_Room) {
                    Toast.makeText(TextualRoutingActivity.this, "You are here !", Toast.LENGTH_SHORT).show();
                    return;
                }
                // an indicator as the path is generated
                if (Graph.Str2 == "ok got all paths") {
                    String path = "";
                    // to hold array of all rooms in the path
                    String[] stre = Graph.str1.split("->");

                    for (int i = 0; i < stre.length - 1; i += 1) {

                        //error solved in algorithm//--
                        if (stre[i].contains("null"))
                            stre[i] = stre[i].replace("null", " ");
                        if (stre[i].contains("الاولباب"))
                            stre[i] = stre[i].replace("الاولباب", "الاول باب"); //--
                        //add a room to the path text
                        path += stre[i] + " " + "->" + "\n" + "\n";
                    }
                    path += destinationRoom;// also add dest. room to the path
                    // send the path text to ShowIndoorPathActivity to show path.
                    intent = new Intent(getApplicationContext(), ShowIndoorPathActivity.class);
                    intent.removeExtra("path");
                    intent.putExtra("path", path);
                    startActivity(intent);

                }
            }
        });


        ////here go to VisualizedRoutingActivity
        tryVisualized_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), VisualizedRoutingActivity.class);
                startActivity(i);
            }
        });
    }

    Graph.Edge[] edgesArray;

    void buildEdgesBetRooms() {


        Graph.Edge[] edgesBetRooms = {

                // ground floor
                new Graph.Edge(getString(R.string.nodex69), getString(R.string.nodex70), 5),
                new Graph.Edge(getString(R.string.nodex69), getString(R.string.nodex71), 9),
                new Graph.Edge(getString(R.string.nodex71), getString(R.string.nodex72), 24),
                new Graph.Edge(getString(R.string.nodex73), getString(R.string.nodex72), 7),
                new Graph.Edge(getString(R.string.nodex74), getString(R.string.nodex73), 9),
                new Graph.Edge(getString(R.string.nodex74), getString(R.string.nodex75), 16),
                new Graph.Edge(getString(R.string.nodex69), getString(R.string.nodex76), 14),
                new Graph.Edge(getString(R.string.nodex76), getString(R.string.nodex77), 21),
                new Graph.Edge(getString(R.string.nodex76), getString(R.string.nodex78), 21),
                new Graph.Edge(getString(R.string.nodex22), getString(R.string.nodex8), 5),
                new Graph.Edge(getString(R.string.nodex2), getString(R.string.nodex25), 5),
                new Graph.Edge(getString(R.string.nodex77), getString(R.string.nodex78), 7),
                new Graph.Edge(getString(R.string.nodex65), getString(R.string.nodex78), 56),
                new Graph.Edge(getString(R.string.nodex65), getString(R.string.nodex30), 19),
                new Graph.Edge(getString(R.string.nodex66), getString(R.string.nodex78), 45),
                new Graph.Edge(getString(R.string.nodex67), getString(R.string.nodex78), 40),
                new Graph.Edge(getString(R.string.nodex66), getString(R.string.nodex68), 9),
                new Graph.Edge(getString(R.string.nodex67), getString(R.string.nodex66), 12),
                new Graph.Edge(getString(R.string.nodex67), getString(R.string.nodex68), 14),

                new Graph.Edge(getString(R.string.nodex30), getString(R.string.nodex60), 32),
                new Graph.Edge(getString(R.string.nodex61), getString(R.string.nodex60), 6),
                new Graph.Edge(getString(R.string.nodex62), getString(R.string.nodex61), 10),
                new Graph.Edge(getString(R.string.nodex77), getString(R.string.nodex63), 16),
                new Graph.Edge(getString(R.string.nodex63), getString(R.string.nodex64), 7),

                new Graph.Edge(getString(R.string.nodex53), getString(R.string.nodex64), 7),
                new Graph.Edge(getString(R.string.nodex53), getString(R.string.nodex54), 15),
                new Graph.Edge(getString(R.string.nodex54), getString(R.string.nodex55), 7),
                new Graph.Edge(getString(R.string.nodex55), getString(R.string.nodex56), 14),
                new Graph.Edge(getString(R.string.nodex56), getString(R.string.nodex57), 5),
                new Graph.Edge(getString(R.string.nodex57), getString(R.string.nodex58), 8),
                new Graph.Edge(getString(R.string.nodex58), getString(R.string.nodex59), 11),
                new Graph.Edge(getString(R.string.nodex58), getString(R.string.nodex69), 60),
                new Graph.Edge(getString(R.string.nodex69), getString(R.string.nodex59), 50),

                // The First Floor.
                new Graph.Edge(getString(R.string.nodex53), getString(R.string.nodex48), 7),
                new Graph.Edge(getString(R.string.nodex48), getString(R.string.nodex54), 15),
                new Graph.Edge(getString(R.string.nodex48), getString(R.string.nodex56), 15),
                new Graph.Edge(getString(R.string.nodex48), getString(R.string.nodex49), 10),
                new Graph.Edge(getString(R.string.nodex48), getString(R.string.nodex50), 15),
                new Graph.Edge(getString(R.string.nodex49), getString(R.string.nodex50), 15),
                new Graph.Edge(getString(R.string.nodex50), getString(R.string.nodex51), 5),
                new Graph.Edge(getString(R.string.nodex51), getString(R.string.nodex52), 10),
                new Graph.Edge(getString(R.string.nodex49), getString(R.string.nodex16), 10),
                new Graph.Edge(getString(R.string.nodex3), getString(R.string.nodex16), 1),
                new Graph.Edge(getString(R.string.nodex48), getString(R.string.nodex16), 10),
                new Graph.Edge(getString(R.string.nodex79), getString(R.string.nodex58), 10),
                new Graph.Edge(getString(R.string.nodex79), getString(R.string.nodex69), 10),
                new Graph.Edge(getString(R.string.nodex49), getString(R.string.nodex33), 7),
                new Graph.Edge(getString(R.string.nodex33), getString(R.string.nodex34), 7),
                new Graph.Edge(getString(R.string.nodex34), getString(R.string.nodex35), 12),
                new Graph.Edge(getString(R.string.nodex35), getString(R.string.nodex36), 9),
                new Graph.Edge(getString(R.string.nodex36), getString(R.string.nodex37), 13),
                new Graph.Edge(getString(R.string.nodex37), getString(R.string.nodex38), 11),
                new Graph.Edge(getString(R.string.nodex37), getString(R.string.nodex39), 12),
                new Graph.Edge(getString(R.string.nodex69), getString(R.string.nodex39), 6),
                new Graph.Edge(getString(R.string.nodex39), getString(R.string.nodex70), 10),
                new Graph.Edge(getString(R.string.nodex38), getString(R.string.nodex43), 10),
                new Graph.Edge(getString(R.string.nodex41), getString(R.string.nodex42), 10),
                new Graph.Edge(getString(R.string.nodex41), getString(R.string.nodex43), 10),
                new Graph.Edge(getString(R.string.nodex43), getString(R.string.nodex44), 6),
                new Graph.Edge(getString(R.string.nodex44), getString(R.string.nodex45), 9),
                new Graph.Edge(getString(R.string.nodex45), getString(R.string.nodex46), 6),
                new Graph.Edge(getString(R.string.nodex46), getString(R.string.nodex47), 30),
                new Graph.Edge(getString(R.string.nodex46), getString(R.string.nodex24), 24),
                new Graph.Edge(getString(R.string.nodex24), getString(R.string.nodex25), 10),
                new Graph.Edge(getString(R.string.nodex47), getString(R.string.nodex26), 13),
                new Graph.Edge(getString(R.string.nodex26), getString(R.string.nodex29), 9),
                new Graph.Edge(getString(R.string.nodex26), getString(R.string.nodex27), 9),
                new Graph.Edge(getString(R.string.nodex30), getString(R.string.nodex31), 18),
                new Graph.Edge(getString(R.string.nodex31), getString(R.string.nodex32), 5),
                new Graph.Edge(getString(R.string.nodex45), getString(R.string.nodex11), 8),
                new Graph.Edge(getString(R.string.nodex45), getString(R.string.nodex26), 8),
                new Graph.Edge(getString(R.string.nodex11), getString(R.string.nodex12), 11),
                new Graph.Edge(getString(R.string.nodex12), getString(R.string.nodex13), 7),
                new Graph.Edge(getString(R.string.nodex13), getString(R.string.nodex14), 8),
                new Graph.Edge(getString(R.string.nodex14), getString(R.string.nodex15), 5),
                new Graph.Edge(getString(R.string.nodex15), getString(R.string.nodex16), 10),
                new Graph.Edge(getString(R.string.nodex15), getString(R.string.nodex17), 9),
                new Graph.Edge(getString(R.string.nodex17), getString(R.string.nodex18), 6),
                new Graph.Edge(getString(R.string.nodex19), getString(R.string.nodex20), 10),
                new Graph.Edge(getString(R.string.nodex19), getString(R.string.nodex21), 8),
                new Graph.Edge(getString(R.string.nodex22), getString(R.string.nodex21), 10),
                new Graph.Edge(getString(R.string.nodex41), getString(R.string.nodex23), 30),
                new Graph.Edge(getString(R.string.nodex19), getString(R.string.nodex23), 25),
                new Graph.Edge(getString(R.string.nodex23), getString(R.string.nodex1), 9),
                new Graph.Edge(getString(R.string.nodex1), getString(R.string.nodex2), 10),
                new Graph.Edge(getString(R.string.nodex3), getString(R.string.nodex4), 8),
                new Graph.Edge(getString(R.string.nodex4), getString(R.string.nodex5), 7),
                new Graph.Edge(getString(R.string.nodex5), getString(R.string.nodex6), 8),
                new Graph.Edge(getString(R.string.nodex6), getString(R.string.nodex7), 8),
                new Graph.Edge(getString(R.string.nodex7), getString(R.string.nodex8), 8),
                new Graph.Edge(getString(R.string.nodex4), getString(R.string.nodex9), 10),
                new Graph.Edge(getString(R.string.nodex9), getString(R.string.nodex10), 9)};

        edgesArray = edgesBetRooms;
        edgesBetRooms = null;
    }
}
