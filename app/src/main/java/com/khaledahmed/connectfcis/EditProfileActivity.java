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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.khaledahmed.connectfcis.AdapterClasses.UserPicture;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    ImageView userImage;
    EditText userName;
    EditText passWord;
    EditText Deptartment;
    EditText Academicyear;
    EditText status;
    EditText infoabout;
    Button save;

    String baseofImageOne;

    String UserNAM;
    String PassWorD;
    String DEPT;
    String ACADEMIC;
    String STATU;
    String INFO;
    String id;
    private static final int SELECT_SINGLE_PICTUREone = 101;
    public static final String IMAGE_TYPE = "image/*";

    private SharedPreferences editPreferencesStudent;

    private  SharedPreferences.Editor editPrefsEditorStudent;

    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;


    String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        GetUserID();

        loginPreferences = getSharedPreferences("forgotpass", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
         Email = loginPreferences.getString("Email", "");
        userImage = (ImageView) findViewById(R.id.userProfileImage);
        userName = (EditText) findViewById(R.id.username_txt);
        passWord = (EditText) findViewById(R.id.password_text);
        Deptartment = (EditText) findViewById(R.id.dept_txt);
        status = (EditText) findViewById(R.id.status_txt);
        Academicyear = (EditText) findViewById(R.id.stdYear);
        infoabout = (EditText) findViewById(R.id.userInfo_txt);
        save = (Button) findViewById(R.id.savechangeBTN);



        editPreferencesStudent = getSharedPreferences("editPrefsuser", MODE_PRIVATE);
        editPrefsEditorStudent = editPreferencesStudent.edit();
       // FlagToSetData=editPreferences.getBoolean("flag",false);
//        String UserNam = editPreferences.getString("usern", "");
//        if(!UserNam.equals(""))
//        {
//            userName.setText(UserNam);
//        }
//        String Pass=editPreferences.getString("editpas","");
//        if(!Pass.equals(""))
//        {
//            passWord.setText(UserNam);
//        }
        String Dept=editPreferencesStudent.getString("editdept","");
        if(!Dept.equals(""))
        {
            Deptartment.setText(Dept);
        }
        String statu=editPreferencesStudent.getString("editstat","");
        if(!statu.equals(""))
        {
            status.setText(statu);
        }
        String acadmeic=editPreferencesStudent.getString("editacademic","");
        if(!acadmeic.equals(""))
        {
            Academicyear.setText(acadmeic);
        }
        String ifoabo=editPreferencesStudent.getString("editinfo","");
        if(!ifoabo.equals(""))
        {
            infoabout.setText(ifoabo);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editPrefsEditorStudent.clear();
                editPrefsEditorStudent.commit();



                 UserNAM=userName.getText().toString();
                PassWorD=passWord.getText().toString();
                DEPT=Deptartment.getText().toString();
                 ACADEMIC=Academicyear.getText().toString();
                 STATU=status.getText().toString();
                 INFO=infoabout.getText().toString();

//                editPrefsEditor.putString("usern",UserNAM);
//                editPrefsEditor.putString("editpas",PassWorD);
                editPrefsEditorStudent.putString("editdept",DEPT);
                editPrefsEditorStudent.putString("editacademic",ACADEMIC);
                editPrefsEditorStudent.putString("editstat",STATU);
                editPrefsEditorStudent.putString("editinfo",INFO);
                editPrefsEditorStudent.commit();
                UpdateUserData();
                Toast.makeText(EditProfileActivity.this, "Done Updated", Toast.LENGTH_SHORT).show();

            }
        });













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



    void GetUserID() {


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

                params.put("email",Email);


                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    void UpdateUserData() {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        ///////// the url to  webService
        String url = "http://192.168.1.9/connectfcis/v1/user/editStdProfile";

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
                params.put("stdDept",DEPT );
                params.put("stdAcadYear", ACADEMIC);
                params.put("studImage",baseofImageOne );
                params.put("availableStatus",STATU );
                params.put("info", INFO);






                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



}