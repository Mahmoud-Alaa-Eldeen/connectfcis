package com.khaledahmed.connectfcis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.khaledahmed.connectfcis.AdapterClasses.UserPicture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {
    ImageView userImage;
    EditText userName;
    EditText passWord;
    EditText Stafftype;
    EditText Deptartment;
    EditText Courses;
    EditText officeLoaction;
    EditText status;
    EditText infoabout;
    Button save;
    String baseofImageOne;

    String UserNAM;
    String PassWorD;
    String DEPT;
    String StaffType;
    String STATU;
    String OfficeLoaction;
    String CoursesData;
    String INFO;
    String id;
    private static final int SELECT_SINGLE_PICTUREone = 101;
    public static final String IMAGE_TYPE = "image/*";

    private SharedPreferences editPreferencesStaff;

    private  SharedPreferences.Editor editPrefsEditorStaff;

    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;

    String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        loginPreferences = getSharedPreferences("forgotpass", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        Email = loginPreferences.getString("Email", "");
        IDOFSTAFF();
        userImage=(ImageView)findViewById(R.id.userProfileImage);
        userName=(EditText)findViewById(R.id.username_txt);
        passWord=(EditText)findViewById(R.id.password_text);
        Stafftype=(EditText)findViewById(R.id.type_text);
        Deptartment=(EditText)findViewById(R.id.dept_txt);
        Courses=(EditText)findViewById(R.id.course_txt);
        officeLoaction=(EditText)findViewById(R.id.officeLoc_txt);
        status=(EditText)findViewById(R.id.status_txt);
        infoabout=(EditText)findViewById(R.id.userInfo_txt);
        save =(Button) findViewById(R.id.savechangeBTN);

        editPreferencesStaff = getSharedPreferences("editPrefstaff", MODE_PRIVATE);
        editPrefsEditorStaff = editPreferencesStaff.edit();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 UserNAM=userName.getText().toString();
                 PassWorD=passWord.getText().toString();
                 DEPT=Deptartment.getText().toString();

                 StaffType=Stafftype.getText().toString();
                 STATU=status.getText().toString();
                 OfficeLoaction=officeLoaction.getText().toString();
                 CoursesData=Courses.getText().toString();
                 INFO=infoabout.getText().toString();

                UpdateDattOFSataff();
                Toast.makeText(UserProfileActivity.this, "Done Updated", Toast.LENGTH_SHORT).show();



            }
        });


//
//        editProfileButton = (ImageButton) findViewById(R.id.editProfileButton);
//        editProfileButton.setImageResource(R.mipmap.ic_mode_edit_black_24dp);
//        editProfileButton.setTag(R.mipmap.ic_mode_edit_black_24dp);
//        resource = (Integer) editProfileButton.getTag();
//        profilePictureSelectButton = (ImageView) findViewById(R.id.userProfileImage);
//        usernameField = (TextView) findViewById(R.id.username_txt);
//        academicYearField = (TextView) findViewById(R.id.aYear_txt);
//        departmentField = (TextView) findViewById(R.id.dept_txt);
//        courseField = (TextView) findViewById(R.id.course_txt);
//        officeLocationField = (TextView) findViewById(R.id.officeLoc_txt);
//        statusField = (TextView) findViewById(R.id.status_txt);
//        userInfo = (TextView) findViewById(R.id.userInfo_txt);


//        editProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                finish();
//                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
////                if (resource == R.mipmap.ic_mode_edit_black_24dp) {
////                    editProfileButton.setImageResource(R.mipmap.ic_save_black_24dp);
////                    editProfileButton.setTag(R.mipmap.ic_save_black_24dp);
////                    resource = (Integer) editProfileButton.getTag();
////                    usernameField.setBackground(getResources().getDrawable(R.drawable.input_background));
////                    passwordField.setBackground(getResources().getDrawable(R.drawable.input_background));
////                    profilePictureSelectButton.setClickable(true);
////                    usernameField.setEnabled(true);
////                    passwordField.setEnabled(true);
////                    visibilitySwitch.setEnabled(true);
////                    academicYearSpinner.setEnabled(true);
////                    departmentSpinner.setEnabled(true);
////                } else {
////
////
////                    //edit function is here
////
////                    recreate();
////                }
//            }
//        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType(IMAGE_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        getString(R.string.select_pictureone)), SELECT_SINGLE_PICTUREone);
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTUREone) {

                Uri selectedImageUri = data.getData();
                try {
                    userImage.setImageBitmap(new UserPicture(selectedImageUri, getContentResolver()).getBitmap());
                    Bitmap bitmap = new UserPicture(selectedImageUri, getContentResolver()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    baseofImageOne = Base64.encodeToString(bytes, Base64.DEFAULT);

//                    byte[]arrayofByte=Base64.decode(baseofImage,Base64.DEFAULT);
//                    Bitmap bitmap1= BitmapFactory.decodeByteArray(arrayofByte,0,arrayofByte.length);
//                    selectedImagePreview.setImageBitmap(bitmap1);
//                    Toast.makeText(MainActivity.this, ""+baseofImage, Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    Log.e(EditProfileActivity.class.getSimpleName(), "Failed to load image", e);
                }

            }
        }
    }


    void IDOFSTAFF() {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        ///////// the url to  webService
        String url = "http://192.168.1.9/connectfcis/v1/user/idOfMail";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("users");
                            //
                            JSONObject object= (JSONObject) jsonArray.get(0);
                             id=   object.getString("userID");


                        } catch (JSONException e) {

                            e.printStackTrace();

                            String [] str=response.split(":");
                            String messgae=  str[2].replace('}',' '); messgae= str[2].replace('"',' ');


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            //define params to be sent when connection opened
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("email", Email);


                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    void UpdateDattOFSataff() {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        ///////// the url to  webService
        String url = "http://192.168.1.9/connectfcis/v1/user/editstaffProfile";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject jsonObject = new JSONObject(response);


                        } catch (JSONException e) {

                            e.printStackTrace();

                            String [] str=response.split(":");
                            String messgae=  str[2].replace('}',' '); messgae= str[2].replace('"',' ');


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            //define params to be sent when connection opened
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();








                params.put("userID",id );
                params.put("userName",UserNAM );
                params.put(" password",PassWorD );
                params.put("staffDept", DEPT);
                params.put("staffSubjects",CoursesData );
                params.put("staffType",StaffType );
                params.put("roomLocation", OfficeLoaction);
                params.put("info",INFO );
                params.put("staffImage",baseofImageOne );

                params.put("availblestatus",STATU );





                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
