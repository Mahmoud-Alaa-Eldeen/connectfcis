package com.khaledahmed.connectfcis.Utilities;
/*
get src and dest input form visualizedRouting, then:-
build a graph of rooms in firest floor, then
and send src and dest to the graph, then get the path, then
send 2 pixels a time to draw on image then get the image with the path drawn to show it .

*/
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.games.multiplayer.realtime.Room;
import com.khaledahmed.connectfcis.IndoorVisualization.Generate_Path;
import com.khaledahmed.connectfcis.R;
import com.khaledahmed.connectfcis.Routing.IndoorRouting.Graph;
import com.khaledahmed.connectfcis.RoutingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Visualized_Indoor extends AppCompatActivity {


HashMap<String,Integer> map;
    ArrayList<Integer> Rooms_pixels;
    Bitmap bitmap;

    Bitmap bmp=null;
    ImageView imageView;
    int width; int height; int[] pixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualized__indoor);

        Rooms_pixels=new ArrayList<Integer>();

        imageView=(ImageView)findViewById(R.id.imageView) ;
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.first);

          //for drawing on bitmap

         width = bitmap.getWidth();
         height = bitmap.getHeight();
         pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);


        // map
        map=new HashMap<String,Integer>();

        map.put("العميد", 190000-5050); //right pixel value.
        map.put("سكرتارية العميد",190000-5050);//
        map.put("غرفة الاجتماعات",190000-5050);//
        map.put("سكرتارية وكيل البيئة",190000-5050);//


        map.put("مدير مركز الحساب العلمي",190000-4770+140-13*633-85+13*633);//
        map.put("قاعة السيمنار",190000-4770+140-13*633-85+13*633);//

        map.put("وكيل الكلية للدراسات العليا",190000-4845);//

        map.put("د تيمور نظمي", 190000-4910);//
        map.put("أمين الكلية",190000-4910);//

        map.put("رئيس قسم حسابات علمية",190000-4770+140);//

        map.put("غرفة 115",190000-4770+140-13*633);//


        map.put("وكيل الكلية لشئون البيئة",190000-5050-85*633);//
        map.put("سكرتارية وكيل الكلية",190000-5050-85*633);//

        map.put("سلم للدور الاول",190000-4770+140-13*633-85);//

        map.put("حمام رجالي  و حمام حريمي",190000-4770+140-110*633);//

        map.put("وكيل الكلية", 190000-5050-85*633-75*633);//
        map.put("البوفيه", 190000-5050-85*633-75*633) ;//
        map.put("حمام رجالي عند مكتب وكيل الكلية", 190000-5050-85*633-75*633);//

        map.put("أسانسير الدور الاول",190000-5050-85*633-145*633);//
        map.put("سلم خلفي للدور الثاني",190000-5050-85*633-145*633);//


        ///////////  Build Graph


       // Graph visualizedRouting=

        final Graph.Edge[] visualizedRouting = {
                // The badroom and the Ground floor.
                new Graph.Edge("أسانسير الدور الاول", "البوفيه", 5),

                new Graph.Edge("سلم خلفي للدور الثاني", "البوفيه", 5),

             //   new Graph.Edge("حمام رجالي عند مكتب وكيل الكلية", "البوفيه", 5),

                new Graph.Edge("وكيل الكلية","سلم خلفي للدور الثاني" , 5),
                new Graph.Edge("وكيل الكلية","أسانسير الدور الاول" , 5),

                new Graph.Edge("البوفيه","سلم خلفي للدور الثاني" , 5),
                new Graph.Edge("البوفيه","أسانسير الدور الاول" , 5),


//1
                new Graph.Edge("البوفيه", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("حمام رجالي عند مكتب وكيل الكلية", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("وكيل الكلية", "سكرتارية وكيل الكلية", 5),

                new Graph.Edge("البوفيه", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("حمام رجالي عند مكتب وكيل الكلية", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("وكيل الكلية", "وكيل الكلية لشئون البيئة", 5),

//1
                //2
                new Graph.Edge("سكرتارية وكيل البيئة", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("العميد", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("غرفة الاجتماعات", "سكرتارية وكيل الكلية", 5),
                new Graph.Edge("سكرتارية العميد", "سكرتارية وكيل الكلية", 5),


                //2

                //3
                new Graph.Edge("سكرتارية وكيل البيئة", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("العميد", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("غرفة الاجتماعات", "وكيل الكلية لشئون البيئة", 5),
                new Graph.Edge("سكرتارية العميد", "وكيل الكلية لشئون البيئة", 5),


                //3

                //4
                new Graph.Edge("سكرتارية وكيل البيئة", "د تيمور نظمي", 5),
                new Graph.Edge("العميد", "د تيمور نظمي", 5),
                new Graph.Edge("غرفة الاجتماعات", "د تيمور نظمي", 5),
                new Graph.Edge("سكرتارية العميد", "د تيمور نظمي", 5),


                new Graph.Edge("سكرتارية وكيل البيئة", "أمين الكلية", 5),
                new Graph.Edge("العميد", "أمين الكلية", 5),
                new Graph.Edge("غرفة الاجتماعات", "أمين الكلية", 5),
                new Graph.Edge("سكرتارية العميد", "أمين الكلية", 5),

                //4

                //5
                new Graph.Edge("وكيل الكلية للدراسات العليا", "د تيمور نظمي", 5),

                new Graph.Edge("وكيل الكلية للدراسات العليا", "أمين الكلية", 5),

                //5

                //6
                new Graph.Edge("وكيل الكلية للدراسات العليا", "قاعة السيمنار", 5),

                new Graph.Edge("وكيل الكلية للدراسات العليا", "مدير مركز الحساب العلمي", 5),


                //6

              //7
                new Graph.Edge("سلم للدور الاول", "قاعة السيمنار", 5),

                new Graph.Edge("سلم للدور الاول", "مدير مركز الحساب العلمي", 5),

//-
                new Graph.Edge("رئيس قسم حسابات علمية", "قاعة السيمنار", 5),

                new Graph.Edge("رئيس قسم حسابات علمية", "مدير مركز الحساب العلمي", 5),


                //7

                //8
                new Graph.Edge("رئيس قسم حسابات علمية", "غرفة 115", 5),

                //8

                //9
                new Graph.Edge("سلم للدور الاول", "غرفة 115", 5),
                new Graph.Edge("حمام رجالي  و حمام حريمي", "غرفة 115", 5)


                //9


        };


        ///here :-  get user actions from visualizedRouting, then send src and dest names to algo ,then get the path and
    // and send 2 nodes int values at once to   be drawn !


      String srcAndDest=  getIntent().getExtras().getString("srcAndDest");
        String[] srcandDest=srcAndDest.split(",");

        String tmpDest=srcandDest[srcandDest.length-1];

        if( map.get(srcandDest[0]) .equals(map.get(tmpDest ))){
            Log.d("src111111111",String.valueOf(map.get(srcandDest[0])));
             Log.d("src22222",String.valueOf(map.get(tmpDest)));


            Toast.makeText(Visualized_Indoor.this, "You are here !", Toast.LENGTH_LONG).show();
            return;}

        Graph g = new Graph(visualizedRouting);

        g.dijkstra(srcandDest[0]);
        g.printPath(srcandDest[1]);



        if (Graph.Str2 == "ok got all paths") {

            String path = "";
            String[] stre = Graph.str1.split("->");

            for (int i = 0; i < stre.length; i += 1) {


               Rooms_pixels.add(map.get(stre[i]));

            }
            if((Rooms_pixels.get(0)==Rooms_pixels.get(Rooms_pixels.size()-1) )||( map.get(srcandDest[0]) ==map.get(srcandDest[srcandDest.length-1] )))
            {
               pixels[Rooms_pixels.get(0)]=Color.GREEN;
                pixels[Rooms_pixels.get(0)-1]=Color.GREEN;
                pixels[Rooms_pixels.get(0)-2]=Color.GREEN;
                pixels[Rooms_pixels.get(0)+1]=Color.GREEN;
                pixels[Rooms_pixels.get(0)+2]=Color.GREEN;
                Toast.makeText(this, "You are Here !", Toast.LENGTH_SHORT).show();


            }
// call the func here /////////////////

            bmp=  drawFunc();
            }

        if(bmp!=null)
        imageView.setImageBitmap(bmp);





        }

    public  Bitmap drawFunc(){




        int srcPixel, destPixel;
        for(int f=0;f<Rooms_pixels.size()-1;f++) {
            srcPixel= Rooms_pixels.get(f);destPixel=Rooms_pixels.get(f+1);

            for (int i = srcPixel; i != destPixel; ) {

                //if src==dest return from the activity.
                if (Math.abs(destPixel - i) >= 633) {

                    if (srcPixel < destPixel) {


                        pixels[i] = Color.RED;
                        i += 633;

                    } else if (srcPixel > destPixel)

                    {


                        pixels[i] = Color.RED;
                        i -= 633;

                    }

                    //then difff is less a row (in the same row of pixels)


                } else if (Math.abs(destPixel - i) < 633) {

                    if (srcPixel > destPixel) {

                        pixels[i] = Color.RED;
                        i -= 1;
                    } else if (srcPixel < destPixel) {

                        pixels[i] = Color.RED;
                        i += 1;

                    }
                }

            }
        }
        ///55
        Bitmap newImage = Bitmap.createBitmap(width, height, bitmap.getConfig());
        newImage.setPixels(pixels, 0, width, 0, 0, width, height);

        return newImage;
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