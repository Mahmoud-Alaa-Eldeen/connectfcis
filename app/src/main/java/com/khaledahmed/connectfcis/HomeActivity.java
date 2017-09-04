package com.khaledahmed.connectfcis;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.khaledahmed.connectfcis.AdapterClasses.EventAdapter;
import com.khaledahmed.connectfcis.ParsingKey.EventKies;
import com.khaledahmed.connectfcis.Structure.Event;
import com.khaledahmed.connectfcis.Routing.OutdoorRouting.MapsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // start from here
    EventAdapter adapter;
    Event event;
    int numberofcontextMenu;
    Dialog dialogDelete;
    ListView listView;
    ArrayList<Event> events = new ArrayList<>();
    String keyOFEvent;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = (ListView) findViewById(R.id.allevents);
        registerForContextMenu(listView);
        stringRequest();
        //  ArrayList<Event>myevent=  stringRequest();

//        eventsRecyclerView = (RecyclerView) findViewById(R.id.event_list);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
//        eventsRecyclerView.setLayoutManager(layoutManager);
//
//        adapter = new EventAdapter(myevent, getApplicationContext(), this);
//
//
//        eventsRecyclerView.setAdapter(adapter);
//        eventsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        adapter.notifyDataSetChanged();

///get events from webservice

        //get events Jsonarray  from webservice then set to array of event object dataType.
        // then show into the UI


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.join_group_btn);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stringRequest();
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_route_indoor) {
            startActivity(new Intent(getApplicationContext(), RoutingActivity.class));
        } else if (id == R.id.nav_route_outdoor) {
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        } else if (id == R.id.nav_my_profile) {


            loginPreferences = getSharedPreferences("forgotpass", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();
            String TypeOfUser = loginPreferences.getString("TypeOfUser", "");
            Toast.makeText(HomeActivity.this, "TypeOfUser"+TypeOfUser, Toast.LENGTH_SHORT).show();
            if(TypeOfUser.equals("Student"))
            {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));

            }
            else if(TypeOfUser.equals("Staff"))
            {
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));

            }



//        } else if (id == R.id.nav_sign_in) {
//            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//        } else if (id == R.id.nav_sign_out) {
//            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
       } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    void stringRequest() {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        ///////// the url to  webService
        String url = "http://192.168.1.9/connectfcis/v1/user/getEvents";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("responseAfterInserting", response);

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("events");
                            // Toast.makeText(HomeActivity.this, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = (JSONObject) jsonArray.get(i);
                                Event event = new Event(object.getString(EventKies.eventName), object.getString(EventKies.eventDesc), object.getString(EventKies.creatTime), object.getString(EventKies.startTime), object.getString(EventKies.finishTime), object.getString(EventKies.eventImage));
                                events.add(event);
                            }


                            adapter = new EventAdapter(events, getApplicationContext(), HomeActivity.this);

                            listView.setAdapter(adapter);
                            //eventsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                            adapter.notifyDataSetChanged();


                            //       Toast.makeText(HomeActivity.this, "MyEvent"+events.size(), Toast.LENGTH_SHORT).show();

//                            eventsRecyclerView = (RecyclerView) findViewById(R.id.event_list);
//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                            eventsRecyclerView.setLayoutManager(layoutManager);
//
//                            adapter = new EventAdapter(events, getApplicationContext(),HomeActivity.this);
//
//                            eventsRecyclerView.setAdapter(adapter);
//                            eventsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//                            adapter.notifyDataSetChanged();
                            /////////////////////set to the ui of home page
//                            for(int i =0;i<events.size();i++) {
//                                Toast.makeText(HomeActivity.this, "L22222", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(),events.get(i).getEventDescription() , Toast.LENGTH_LONG).show();
//                                Toast.makeText(getApplicationContext(),events.get(i).getEventTitle() , Toast.LENGTH_LONG).show();
//                                Toast.makeText(getApplicationContext(),events.get(i).getStartDate() , Toast.LENGTH_LONG).show();
//                                Toast.makeText(getApplicationContext(),events.get(i).getCreationDateAndTime() ,Toast.LENGTH_LONG).show();
//                                Toast.makeText(getApplicationContext(),events.get(i).getEndDate() , Toast.LENGTH_LONG).show();
//                                Toast.makeText(getApplicationContext(),events.get(i).getEventImage() , Toast.LENGTH_LONG).show();
//
//                                if (events.get(i).getEventImage()==null)
//
//                                {///////////////////////////////////////////ijjjjjjjjjjjjjjjjj
//                                    // event_image.
//                                    // hide the imageview
//                                    //else, show it in the imageview
//                                }
//
//                            }

                            //here if returned not error, then registered correct
                            if (!jsonObject.getBoolean("Error")) {

                                //       Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                //     intent.putExtra("name", "Ahmed");
//                                startActivity(intent);

                            }

//                            Contact contact = new Gson().fromJson(jsonObject.toString(),Contact.class);

//                            DisplayContext.Type type = new TypeToken<List<Contact>>() {
//                            }.getType();
//
//                            List<Contact> contacts = new Gson().fromJson(jsonObject.getJSONArray("contacts").toString(), type);
//
//                            Log.d("response", contacts.get(0).getPhoneData().getMobileNumber());
//                            Log.d("response", contacts.get(0).getName());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("responce", "hat didn't work!");
            }
        }) {

            //define params to be sent when connection opened
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
//                String firstname = firstNameEditText.getText().toString();
//                String lname = secondNameEditText.getText().toString();
//                String email = emailEditText.getText().toString();
//                String pass = passswordEditText.getText().toString();
//                // fname is the name in thw webrevice
//                params.put("fName", firstname);
//                params.put("lName", lname);
//                params.put("email", email);
//                params.put("password", pass);


                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


//     void recyclerMain() {
//
//        eventsRecyclerView = (RecyclerView) findViewById(R.id.event_list);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        eventsRecyclerView.setLayoutManager(layoutManager);
//
//        adapter = new EventAdapter(events, getApplicationContext(), this);
//
//        eventsRecyclerView.setAdapter(adapter);
//        eventsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        adapter.notifyDataSetChanged();
//
//    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        numberofcontextMenu = info.position;
        switch (item.getItemId()) {

            case R.id.cooment:
                event = events.get(numberofcontextMenu);
                String EventTit = event.getEventTitle();
                Intent i = new Intent(HomeActivity.this, Comments.class);

                i.putExtra("TitleE", EventTit);
                startActivity(i);

                return true;

        }
        return false;
    }


}
