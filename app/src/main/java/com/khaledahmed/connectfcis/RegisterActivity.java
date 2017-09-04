package com.khaledahmed.connectfcis;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.khaledahmed.connectfcis.Date.DialogHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    private EditText firstname_field;
    private EditText lastname_field;
    private EditText email_field;
    private EditText password_field;
    private EditText user_name;
    private RadioButton male_RadioButton;
    private RadioButton female_RadioButton;
    private ImageButton birthdate_Button;
    private TextView birthdate_TextView;
    private Spinner usertype_Spinner;
    private Button register_Button;
    private String FirstName = "";
    String LastName = "";
    String Gender = "";
    String Email = "";
    String UserName = "";
    String Password = "";
    String DateCreateProfile = "";
    String TypeOfUser = "";
    private ArrayAdapter<String> userTypeAdapter;
    private String[] userTypeList;




    SharedPreferences prefs ;
    SharedPreferences.Editor editor ;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firstname_field = (EditText) findViewById(R.id.first_name_input);
        lastname_field = (EditText) findViewById(R.id.last_name_input);
        email_field = (EditText) findViewById(R.id.email_input);
        password_field = (EditText) findViewById(R.id.password_field);
        user_name = (EditText) findViewById(R.id.username_input);
        male_RadioButton = (RadioButton) findViewById(R.id.male_rb);
        female_RadioButton = (RadioButton) findViewById(R.id.female_rb);
        birthdate_Button = (ImageButton) findViewById(R.id.birth_date_btn);
        birthdate_TextView = (TextView) findViewById(R.id.birth_date_field);
        usertype_Spinner = (Spinner) findViewById(R.id.user_type_spin);
        register_Button = (Button) findViewById(R.id.register_btn);

        userTypeList = getResources().getStringArray(R.array.user_types);
        userTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, userTypeList);

        usertype_Spinner.setAdapter(userTypeAdapter);


        male_RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gender = male_RadioButton.getText().toString();

            }
        });


        female_RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gender = female_RadioButton.getText().toString();
            }
        });


        birthdate_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                int year = c.get(java.util.Calendar.YEAR);
                int month = c.get(java.util.Calendar.MONTH);
                int day = c.get(java.util.Calendar.DAY_OF_MONTH);
                DatePickerDialog dp = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        birthdate_TextView.setText(day + "/" + (month + 1) + "/" + year);

                    }
                }, year, month, day);
                dp.show();
            }
        });

        usertype_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TypeOfUser = usertype_Spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });










        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirstName = firstname_field.getText().toString();
                LastName = lastname_field.getText().toString();
                Email = email_field.getText().toString();
                Password = password_field.getText().toString();
                UserName = user_name.getText().toString();
                DateCreateProfile = birthdate_TextView.getText().toString();

                // check if items are empty

                if ((FirstName.equals("")) || (LastName.equals("")) || (Email.equals("")) || (DateCreateProfile.equals("")) ||
                        (Password.equals("")) || UserName.equals("") || Gender.equals("") || TypeOfUser.equals("")) {


                    Toast toast = Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_LONG);//or LENGTH_short
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG); // or LENGTH_short
                    //  toast.setText("ya gmalhooooo");
                    toast.show();
                }

                else {

                    prefs = getSharedPreferences("dataAboutUser", MODE_PRIVATE);
                    editor = prefs.edit();

                    editor.putString("yourStringName", "this_is_the_saved_value");
                    editor.commit();
                    stringRequest();
                }

            }
        });
    }


    void stringRequest() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        ///////// the url to  webService
        String url = "http://"+ getString(R.string.serverIp)+"/connectfcis/v1/user/register";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONObject jsonObject=
                        String jsonObject1="";
                        try {
                            Log.d("responseAfterInserting", response);
                            //Toast.makeText(RegisterActivity.this, "try", Toast.LENGTH_SHORT).show();

                            JSONObject  jsonObject =new JSONObject(response);
                            jsonObject1= jsonObject.getString("message");

                            //here if returned not error, then registered correct
                            Log.d("response1", "bef1");
                            String tmpd=   jsonObject.getString("error");
                            if (!jsonObject.getBoolean("error")) {

                                Toast.makeText(RegisterActivity.this, "if", Toast.LENGTH_SHORT).show();


                                Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                Log.d("response1", "bef2");
                                Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
                                startActivity(intent);
                                Log.d("response", "after");
                                //Toast.makeText(RegisterActivity.this, "Successfully 2 registered", Toast.LENGTH_SHORT).show();
                                jsonObject1= jsonObject.getString("message");
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "else", Toast.LENGTH_SHORT).show();

                                Log.d("response11", "bef11");
                                Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                jsonObject1= jsonObject.getString("message");

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
                //            Toast.makeText(RegisterActivity.this, "in catch", Toast.LENGTH_SHORT).show();

                        }
                        Log.d("response3", response);
                        //split bet message and error found or not
                        String [] tmp=response.split(",");
                        //get message
                        String [] tmp2=tmp[1].split(":");
                     //   if(tmp2[1].equals('"'+"Sorry")){tmp2[1]+=", this email already existed";}
                        Toast.makeText(RegisterActivity.this, tmp2[1], Toast.LENGTH_SHORT).show();
                        String []tmpds=tmp[0].split(":");

                        if(tmpds[1].equals("false"))

                        {
                            loginPreferences = getSharedPreferences("forgotpass", MODE_PRIVATE);
                            loginPrefsEditor = loginPreferences.edit();

                            ///start wrote to this shared Prefernecefile

                            loginPrefsEditor.putString("Email", Email);
                            loginPrefsEditor.putString("Password", Password);
                            loginPrefsEditor.putString("UserName",UserName);
                            loginPrefsEditor.putString("TypeOfUser",TypeOfUser);
                            loginPrefsEditor.commit();


                            //


                            Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("responce", "hat didn't work!");
                Toast.makeText(RegisterActivity.this, "emailnot valid", Toast.LENGTH_SHORT).show();

            }
        }) {

            //define params to be sent when connection opened
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                // fname is the name in thw webrevice
                params.put("fName", FirstName );
                params.put("lName",  LastName);
                params.put("email",   Email );
                params.put("userName", UserName);
                params.put("password", Password);
                params.put("gender",Gender );
                params.put("dateOfBirth",  DateCreateProfile);
                params.put("type",TypeOfUser);

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }




    @Override
    protected void onStart() {
        super.onStart();
    }

    public void showDate(View view) {
        DialogHandler handler = new DialogHandler();
        handler.show(getFragmentManager(), "Time_Picker");
    }
}
