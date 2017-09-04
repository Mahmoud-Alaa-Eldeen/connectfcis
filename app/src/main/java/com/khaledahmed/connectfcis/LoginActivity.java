package com.khaledahmed.connectfcis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class LoginActivity extends AppCompatActivity {
    private  SharedPreferences loginPreferences;
    private  SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    CheckBox remebermeBtn;

    private Button sign_in_btn;
    private EditText username_field;
    private EditText password_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sign_in_btn = (Button) findViewById(R.id.sign_in_btn);
        username_field = (EditText) findViewById(R.id.login_username_txt);
        password_field = (EditText) findViewById(R.id.login_password_txt);
        remebermeBtn=(CheckBox)findViewById(R.id.rememberMeOption_cb);
//        password_field.setTransformationMethod(new PasswordTransformationMethod());


        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
          //  username_field.setText(loginPreferences.getString("username", ""));
            //password_field.setText(loginPreferences.getString("password", ""));
            //remebermeBtn.setChecked(true);
            Intent i=new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(i);
        }

        remebermeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=username_field.getText().toString();
                String password=password_field.getText().toString();
                if (remebermeBtn.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }
            }
        });


        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                stringRequest();

            }
        });
    }

    void stringRequest() {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        ///////// the url to  webService
        String url = "http://"+ getString(R.string.serverIp)+"/connectfcis/v1/user/login";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("responseAfterLogin", response);


                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(LoginActivity.this, "welcome "+jsonObject.getString("firstName"), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();


                        } catch (JSONException e) {
                            Log.d("responseAfterLogin", response);
                            e.printStackTrace();
                       //     Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                      String [] str=response.split(":");
                            Toast.makeText(LoginActivity.this,str[2] , Toast.LENGTH_SHORT).show();

                        }
                       // Toast.makeText(LoginActivity.this, , Toast.LENGTH_SHORT).show();

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

                String email= username_field.getText().toString();
                String password=password_field.getText().toString();
                // fname is the name in thw webrevice
                params.put("email", email);
                params.put("password", password);


                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void skipToHome(View view) {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    public void goToRegister(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
    }
}
