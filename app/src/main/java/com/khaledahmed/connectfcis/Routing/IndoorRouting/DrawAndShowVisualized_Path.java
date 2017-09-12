package com.khaledahmed.connectfcis.Routing.IndoorRouting;
/**
 * get the path, draw it on image and show it.// in details:-
 * get selected src and dest input form visualizedRoutingActivity, then:-
 * build a graph of rooms  (Applied on first floor), then
 * send the selected src and dest to the Graph Algorithm, then get the path, then
 * draw the path 2 pixels a time(of all pixels in the path) to draw on image, then get the image with the path drawn to show it .
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.khaledahmed.connectfcis.R;
import com.khaledahmed.connectfcis.Routing.IndoorRouting.IndoorRoutingAlgorithm.Graph;

import java.util.ArrayList;
import java.util.HashMap;

public class DrawAndShowVisualized_Path extends AppCompatActivity {


    HashMap<String, Integer> map; // to hold all first floor rooms as (name, pixel)
    ArrayList<Integer> PathAsPixels;// to hold all pixels in path from src to dest rooms
    Bitmap bitmap;

    Bitmap bmp = null;
    ImageView imageView;
    int width;
    int height;
    int[] pixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualized__indoor);

        PathAsPixels = new ArrayList<Integer>();

        imageView = (ImageView) findViewById(R.id.imageView);
        // get the bitmap of first floor image
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.first);

        width = bitmap.getWidth();
        height = bitmap.getHeight();
        pixels = new int[width * height];// to hold all pixels of bitmap as(1-dimension)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);


        //build map of all first floor rooms
        buildRoomPerPixelMap();

        // Build Edges between first floor rooms (room1, room2, cost)
        buildEdgesArray();


        // then know correspond. pixel num of each room name, and draw the path, and show image
        ///  get src and dest rooms from visualizedRoutingActivity (sent intent)
        String srcAndDest = getIntent().getExtras().getString("srcAndDest");
        String[] srcandDest = srcAndDest.split(",");

        String tmpDest = srcandDest[srcandDest.length - 1];

        // if src & destination rooms are the same.
        if (map.get(srcandDest[0]).equals(map.get(tmpDest))) {
            Log.d("src", String.valueOf(map.get(srcandDest[0])));
            Log.d("dest", String.valueOf(map.get(tmpDest)));


            Toast.makeText(DrawAndShowVisualized_Path.this, "You are here !", Toast.LENGTH_LONG).show();
            return;
        }

        /// create a Graph instance with Edge [] of rooms
        Graph g = new Graph(edgesBetweenRooms);

        g.dijkstra(srcandDest[0]);/// set src room name
        g.findPath(srcandDest[1]);// set dest room name, and find path


        // get the path from src to dest rooms.
        //this is an indicator that a path is generated.
        if (Graph.Str2 == "ok got all paths") {

            // String path = "";// to hold the path as string
            String[] roomsArray = Graph.str1.split("->");// split rooms with '->'

            for (int i = 0; i < roomsArray.length; i += 1) {


                //get correspond. pixel of (each room name in the returned path)
                PathAsPixels.add(map.get(roomsArray[i]));

            }
            // if src is the same as dest, "you are here!". make the color of 4 pixels green
            if ((PathAsPixels.get(0) == PathAsPixels.get(PathAsPixels.size() - 1)) || (map.get(srcandDest[0]) == map.get(srcandDest[srcandDest.length - 1]))) {
                pixels[PathAsPixels.get(0)] = Color.GREEN;
                pixels[PathAsPixels.get(0) - 1] = Color.GREEN;
                pixels[PathAsPixels.get(0) - 2] = Color.GREEN;
                pixels[PathAsPixels.get(0) + 1] = Color.GREEN;
                pixels[PathAsPixels.get(0) + 2] = Color.GREEN;
                Toast.makeText(this, "You are Here !", Toast.LENGTH_SHORT).show();


            }
            // src is not dest, Draw path on image, bmp is the image with drawn path
            bmp = drawFunc();

        }

        // set image with drawn path to image view.
        if (bmp != null)
            imageView.setImageBitmap(bmp);


    }

    public Bitmap drawFunc() {// draw the path on image (draw line bet. each 2 pixels)


        int srcPixel, destPixel;

        for (int f = 0; f < PathAsPixels.size() - 1; f++) {
            srcPixel = PathAsPixels.get(f);//a pixel in the path of pixels array
            destPixel = PathAsPixels.get(f + 1);//next one

            // change color for all pixels bet. these 2 pixels srcPixel,destPixel
            for (int i = srcPixel; i != destPixel; ) {

                //the width of image is 633 pixel
                //if destPixel minus i (current pixel in the path)is >=633,
                // then the path bet. these 2 pixels is vertical
                if (Math.abs(destPixel - i) >= 633) {

                    if (srcPixel < destPixel) {// src <dest (then destPixel>srcPixel num)
                        //and will add 633 to i to get to next pixel vertically

                        pixels[i] = Color.RED;
                        i += 633;

                    } else if (srcPixel > destPixel)// src>dest, minus 633 from i

                    {


                        pixels[i] = Color.RED;
                        i -= 633;

                    }

                    //then diff. is less than 633, means (2 pixels in the same row in image)
                } else if (Math.abs(destPixel - i) < 633) {

                    if (srcPixel > destPixel) {

                        pixels[i] = Color.RED; // minus 1 (as the pixels in the same row)
                        i -= 1;
                    } else if (srcPixel < destPixel) {

                        pixels[i] = Color.RED;
                        i += 1;

                    }
                }

            }
        }
        //create bitmap
        Bitmap newImage = Bitmap.createBitmap(width, height, bitmap.getConfig());
        // set the bitmap data as the pixels that have the drawn path
        newImage.setPixels(pixels, 0, width, 0, 0, width, height);
        return newImage;
    }

    private void buildRoomPerPixelMap() {

        map = new HashMap<String, Integer>();
        map.put("العميد", 190000 - 5050); //real pixel value on first floor image
        map.put("سكرتارية العميد", 190000 - 5050);
        map.put("غرفة الاجتماعات", 190000 - 5050);
        map.put("سكرتارية وكيل البيئة", 190000 - 5050);
        map.put("مدير مركز الحساب العلمي", 190000 - 4770 + 140 - 13 * 633 - 85 + 13 * 633);
        map.put("قاعة السيمنار", 190000 - 4770 + 140 - 13 * 633 - 85 + 13 * 633);
        map.put("وكيل الكلية للدراسات العليا", 190000 - 4845);
        map.put("د تيمور نظمي", 190000 - 4910);
        map.put("أمين الكلية", 190000 - 4910);
        map.put("رئيس قسم حسابات علمية", 190000 - 4770 + 140);
        map.put("غرفة 115", 190000 - 4770 + 140 - 13 * 633);
        map.put("وكيل الكلية لشئون البيئة", 190000 - 5050 - 85 * 633);
        map.put("سكرتارية وكيل الكلية", 190000 - 5050 - 85 * 633);
        map.put("سلم للدور الاول", 190000 - 4770 + 140 - 13 * 633 - 85);
        map.put("حمام رجالي  و حمام حريمي", 190000 - 4770 + 140 - 110 * 633);
        map.put("وكيل الكلية", 190000 - 5050 - 85 * 633 - 75 * 633);
        map.put("البوفيه", 190000 - 5050 - 85 * 633 - 75 * 633);
        map.put("حمام رجالي عند مكتب وكيل الكلية", 190000 - 5050 - 85 * 633 - 75 * 633);
        map.put("أسانسير الدور الاول", 190000 - 5050 - 85 * 633 - 145 * 633);
        map.put("سلم خلفي للدور الثاني", 190000 - 5050 - 85 * 633 - 145 * 633);

    }

    Graph.Edge[] edgesBetweenRooms;

    private void buildEdgesArray() {

        Graph.Edge[] edges = {

                new Graph.Edge("أسانسير الدور الاول", "البوفيه", 5),
                new Graph.Edge("سلم خلفي للدور الثاني", "البوفيه", 5),
                new Graph.Edge("وكيل الكلية", "سلم خلفي للدور الثاني", 5),
                new Graph.Edge("وكيل الكلية", "أسانسير الدور الاول", 5),
                new Graph.Edge("البوفيه", "سلم خلفي للدور الثاني", 5),
                new Graph.Edge("البوفيه", "أسانسير الدور الاول", 5),
                new Graph.Edge("البوفيه", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("حمام رجالي عند مكتب وكيل الكلية", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("وكيل الكلية", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("البوفيه", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("حمام رجالي عند مكتب وكيل الكلية", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("وكيل الكلية", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("سكرتارية وكيل البيئة", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("العميد", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("غرفة الاجتماعات", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("سكرتارية العميد", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("سكرتارية وكيل البيئة", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("العميد", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("غرفة الاجتماعات", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("سكرتارية العميد", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("سكرتارية وكيل البيئة", "د تيمور نظمي", 5),
                new Graph.Edge("العميد", "د تيمور نظمي", 5),
                new Graph.Edge("غرفة الاجتماعات", "د تيمور نظمي", 5),
                new Graph.Edge("سكرتارية العميد", "د تيمور نظمي", 5),
                new Graph.Edge("سكرتارية وكيل البيئة", "أمين الكلية", 5),
                new Graph.Edge("العميد", "أمين الكلية", 5),
                new Graph.Edge("غرفة الاجتماعات", "أمين الكلية", 5),
                new Graph.Edge("سكرتارية العميد", "أمين الكلية", 5),
                new Graph.Edge("وكيل الكلية للدراسات العليا", "د تيمور نظمي", 5),
                new Graph.Edge("وكيل الكلية للدراسات العليا", "أمين الكلية", 5),
                new Graph.Edge("وكيل الكلية للدراسات العليا", "قاعة السيمنار", 5),
                new Graph.Edge("وكيل الكلية للدراسات العليا", "مدير مركز الحساب العلمي", 5),
                new Graph.Edge("سلم للدور الاول", "قاعة السيمنار", 5),
                new Graph.Edge("سلم للدور الاول", "مدير مركز الحساب العلمي", 5),
                new Graph.Edge("رئيس قسم حسابات علمية", "قاعة السيمنار", 5),
                new Graph.Edge("رئيس قسم حسابات علمية", "مدير مركز الحساب العلمي", 5),
                new Graph.Edge("رئيس قسم حسابات علمية", "غرفة 115", 5),
                new Graph.Edge("سلم للدور الاول", "غرفة 115", 5),
                new Graph.Edge("حمام رجالي  و حمام حريمي", "غرفة 115", 5)};

        edgesBetweenRooms = edges;
        edges = null;

    }

}



/*  rooms daTa



  190000-5050    // العميد و اجتماعات و سكرتارية وكيل البيئة و سكرتارية العميد



 قاعة السيمنار    190000-4770   و مدير مركز الحساب العملي


وكيل الكلية للدراسات العليا    190000-4200

امين اكلية و د تيمور نظمي    190000-4280


رئيس قسم حاسبات عليمة       190000-4000


غرفة 115        180000-29435


وكيل الكلية لشئون البيئة    و سكرتارية وكيل الكلية     170000-29370


سلم للدور الاول     160000-29370



حمام رجالي  و حمام حريمي     160000-29060



و كيل الكلية و البوفيه    و حمام رجالي عند وكيل الكلية    100000-29630
///

سلم خلفي للدور الثاني   و اسانسير الدور الاول      60000-30130




*/