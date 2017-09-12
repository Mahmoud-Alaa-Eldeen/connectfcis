package com.khaledahmed.connectfcis.Routing.IndoorRouting;
/*
*get the src & dest rooms selected
*and send to Graph Algo, get path
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
    private Spinner source_spinner;
    private Spinner destination_spinner;
    private Button navigate_button;
    private Intent intent;
    private List<String> nodes = new ArrayList<>();
    private TextView tryVisualized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing);

        //  nodes= new List<String>();

        source_spinner = (Spinner) findViewById(R.id.source_spinner);
        destination_spinner = (Spinner) findViewById(R.id.destination_spinner);
        navigate_button = (Button) findViewById(R.id.navigate);
        tryVisualized = (TextView) findViewById(R.id.tryVisualized);

        final Graph.Edge[] GRAPH = {
                // The badroom and the Ground floor.
                new Graph.Edge(getString(R.string.nodex69), getString(R.string.nodex70), 5),
                new Graph.Edge(getString(R.string.nodex69), getString(R.string.nodex71), 9),
                new Graph.Edge(getString(R.string.nodex71), getString(R.string.nodex72), 24),
                new Graph.Edge(getString(R.string.nodex73), getString(R.string.nodex72), 7),
                new Graph.Edge(getString(R.string.nodex74), getString(R.string.nodex73), 9),
                new Graph.Edge(getString(R.string.nodex74), getString(R.string.nodex75), 16),
                new Graph.Edge(getString(R.string.nodex69), getString(R.string.nodex76), 14),
                new Graph.Edge(getString(R.string.nodex76), getString(R.string.nodex77), 21),
                new Graph.Edge(getString(R.string.nodex76), getString(R.string.nodex78), 21),
//////77777777777777777
                new Graph.Edge(getString(R.string.nodex22), getString(R.string.nodex8), 5),
                new Graph.Edge(getString(R.string.nodex2), getString(R.string.nodex25), 5),

                ///cont reading &checkkkkkkkkkkkkk
                new Graph.Edge(getString(R.string.nodex77), getString(R.string.nodex78), 7),
                new Graph.Edge(getString(R.string.nodex65), getString(R.string.nodex78), 56),
                new Graph.Edge(getString(R.string.nodex65), getString(R.string.nodex30), 19),
                new Graph.Edge(getString(R.string.nodex66), getString(R.string.nodex78), 45),
                new Graph.Edge(getString(R.string.nodex67), getString(R.string.nodex78), 40),
                new Graph.Edge(getString(R.string.nodex66), getString(R.string.nodex68), 9),
                new Graph.Edge(getString(R.string.nodex67), getString(R.string.nodex66), 12),

                new Graph.Edge(getString(R.string.nodex67), getString(R.string.nodex68), 14),

                //-------second paper

                new Graph.Edge(getString(R.string.nodex30), getString(R.string.nodex60), 32),
                new Graph.Edge(getString(R.string.nodex61), getString(R.string.nodex60), 6),
                new Graph.Edge(getString(R.string.nodex62), getString(R.string.nodex61), 10),
                new Graph.Edge(getString(R.string.nodex77), getString(R.string.nodex63), 16),
                new Graph.Edge(getString(R.string.nodex63), getString(R.string.nodex64), 7),


                new Graph.Edge(getString(R.string.nodex53), getString(R.string.nodex64), 7),
                //////4
                new Graph.Edge(getString(R.string.nodex53), getString(R.string.nodex54), 15),
                new Graph.Edge(getString(R.string.nodex54), getString(R.string.nodex55), 7),
                new Graph.Edge(getString(R.string.nodex55), getString(R.string.nodex56), 14),
                new Graph.Edge(getString(R.string.nodex56), getString(R.string.nodex57), 5),
                new Graph.Edge(getString(R.string.nodex57), getString(R.string.nodex58), 8),
                new Graph.Edge(getString(R.string.nodex58), getString(R.string.nodex59), 11),


                //////5


                ////////////////5
                new Graph.Edge(getString(R.string.nodex58), getString(R.string.nodex69), 60),
                new Graph.Edge(getString(R.string.nodex69), getString(R.string.nodex59), 50),
                ///////second part of work. (The First Floor.)
                new Graph.Edge(getString(R.string.nodex53), getString(R.string.nodex48), 7),
                new Graph.Edge(getString(R.string.nodex48), getString(R.string.nodex54), 15),
                new Graph.Edge(getString(R.string.nodex48), getString(R.string.nodex56), 15),
                new Graph.Edge(getString(R.string.nodex48), getString(R.string.nodex49), 10),
                new Graph.Edge(getString(R.string.nodex48), getString(R.string.nodex50), 15),
                new Graph.Edge(getString(R.string.nodex49), getString(R.string.nodex50), 15),
                new Graph.Edge(getString(R.string.nodex50), getString(R.string.nodex51), 5),
                ///////chekkkkkkkkkkkk finalllllllll
                new Graph.Edge(getString(R.string.nodex51), getString(R.string.nodex52), 10),

                new Graph.Edge(getString(R.string.nodex49), getString(R.string.nodex16), 10),
                new Graph.Edge(getString(R.string.nodex3), getString(R.string.nodex16), 1),
//ظظظ
                new Graph.Edge(getString(R.string.nodex48), getString(R.string.nodex16), 10),

                new Graph.Edge(getString(R.string.nodex79), getString(R.string.nodex58), 10),

                new Graph.Edge(getString(R.string.nodex79), getString(R.string.nodex69), 10),


                ///////////////////////////5
                new Graph.Edge(getString(R.string.nodex49), getString(R.string.nodex33), 7),

                //from here 1
                new Graph.Edge(getString(R.string.nodex33), getString(R.string.nodex34), 7),
                new Graph.Edge(getString(R.string.nodex34), getString(R.string.nodex35), 12),
                new Graph.Edge(getString(R.string.nodex35), getString(R.string.nodex36), 9),
                //
                new Graph.Edge(getString(R.string.nodex36), getString(R.string.nodex37), 13),
                new Graph.Edge(getString(R.string.nodex37), getString(R.string.nodex38), 11),
                new Graph.Edge(getString(R.string.nodex37), getString(R.string.nodex39), 12),
                //l
                new Graph.Edge(getString(R.string.nodex69), getString(R.string.nodex39), 6),
                new Graph.Edge(getString(R.string.nodex39), getString(R.string.nodex70), 10),
                //ظظظ//
                new Graph.Edge(getString(R.string.nodex38), getString(R.string.nodex43), 10),
                new Graph.Edge(getString(R.string.nodex41), getString(R.string.nodex42), 10),
                new Graph.Edge(getString(R.string.nodex41), getString(R.string.nodex43), 10),
                new Graph.Edge(getString(R.string.nodex43), getString(R.string.nodex44), 6),
                new Graph.Edge(getString(R.string.nodex44), getString(R.string.nodex45), 9),
                new Graph.Edge(getString(R.string.nodex45), getString(R.string.nodex46), 6),
                new Graph.Edge(getString(R.string.nodex46), getString(R.string.nodex47), 30),


                //////////////////45
                new Graph.Edge(getString(R.string.nodex46), getString(R.string.nodex24), 24),
                new Graph.Edge(getString(R.string.nodex24), getString(R.string.nodex25), 10),
                new Graph.Edge(getString(R.string.nodex47), getString(R.string.nodex26), 13),

                //  new Graph.Edge(getString(R.string.nodex26), getString(R.string.nodex26), 9),

                new Graph.Edge(getString(R.string.nodex26), getString(R.string.nodex29), 9),

                new Graph.Edge(getString(R.string.nodex26), getString(R.string.nodex27), 9),

                new Graph.Edge(getString(R.string.nodex30), getString(R.string.nodex31), 18),
                new Graph.Edge(getString(R.string.nodex31), getString(R.string.nodex32), 5),
                new Graph.Edge(getString(R.string.nodex45), getString(R.string.nodex11), 8),

                new Graph.Edge(getString(R.string.nodex45), getString(R.string.nodex26), 8),

                new Graph.Edge(getString(R.string.nodex11), getString(R.string.nodex12), 11),

                new Graph.Edge(getString(R.string.nodex12), getString(R.string.nodex13), 7),


                new Graph.Edge(getString(R.string.nodex13), getString(R.string.nodex14), 8),
                //   fh
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
////////////////jjjjjjjjjjjjjjjjjjjjj

                new Graph.Edge(getString(R.string.nodex3), getString(R.string.nodex4), 8),


                new Graph.Edge(getString(R.string.nodex4), getString(R.string.nodex5), 7),


                new Graph.Edge(getString(R.string.nodex5), getString(R.string.nodex6), 8),


                new Graph.Edge(getString(R.string.nodex6), getString(R.string.nodex7), 8),


                new Graph.Edge(getString(R.string.nodex7), getString(R.string.nodex8), 8),


                new Graph.Edge(getString(R.string.nodex4), getString(R.string.nodex9), 10),


                new Graph.Edge(getString(R.string.nodex9), getString(R.string.nodex10), 9)

                //->

        };

        ///lllsss


        /////llls
//linkarraykist to the spineer and make it dialouge.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.nodesy));
        source_spinner.setAdapter(adapter);
        destination_spinner.setAdapter(adapter);

        navigate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Graph g = new Graph(GRAPH);
                //from nodesx "summary"
                String START = source_spinner.getSelectedItem().toString();
                String END = destination_spinner.getSelectedItem().toString();
                String STARTsum;
                String ENDsum;
                int srsSelecIndex = source_spinner.getSelectedItemPosition();
                int destSelecIndex = destination_spinner.getSelectedItemPosition();
                //
                String[] some_array = getResources().getStringArray(R.array.nodesx);

                //String[] some_array2orginal = getResources().getStringArray(R.array.nodesx);

                //    String srcindextmp="nodex"+String.valueOf(srsSelecIndex);
                STARTsum = some_array[srsSelecIndex];
                ENDsum = some_array[destSelecIndex];

                g.dijkstra(STARTsum);
                g.findPath(ENDsum);
                //g.printAllPaths();
                //Graph.str1;

                if (STARTsum == ENDsum) {
                    Toast.makeText(TextualRoutingActivity.this, "You are here !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Graph.Str2 == "ok got all paths") {
                    String path = "";
                    String[] stre = Graph.str1.split("->");
                    for (int i = 0; i < stre.length - 1; i += 1) {

                        /*if(i==stre.length-1){
                            path+=some_array2orginal[destSelecIndex];
                            break;}*/
                        if (stre[i].contains("null"))
                            stre[i] = stre[i].replace("null", " ");//continue;
                        if (stre[i].contains("الاولباب"))
                            stre[i] = stre[i].replace("الاولباب", "الاول باب");
                        path += stre[i] + " " + "->" + "\n" + "\n";
                    }
                    path += END;
                    intent = new Intent(getApplicationContext(), ShowIndoorPathActivity.class);
                    intent.removeExtra("path");
                    intent.putExtra("path", path);
                    startActivity(intent);
                    //((TextView) findViewById(R.id.PathToRoom)).setText(path);
                }
            }
        });


        ////here go to visualized routing activity
        tryVisualized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), VisualizedRoutingActivity.class);
                startActivity(i);
            }
        });
    }
}
