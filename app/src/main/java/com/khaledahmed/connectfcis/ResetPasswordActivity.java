package com.khaledahmed.connectfcis;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.khaledahmed.connectfcis.MailClasses.SendMail;

import java.util.Random;

public class ResetPasswordActivity extends AppCompatActivity {

    Button receiveCode;
    Button receivePassword;
    EditText EmailAddress;
    EditText codeNumber;
    String codewhichGenerated;
    String YOURpassword;
    TextView setPassword;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        receiveCode = (Button) findViewById(R.id.button2);
        receivePassword = (Button) findViewById(R.id.button3);
        EmailAddress = (EditText) findViewById(R.id.editText2);
        codeNumber = (EditText) findViewById(R.id.editText3);
        setPassword = (TextView) findViewById(R.id.user_password);

        receiveCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean isLoggedIn = false;
                String Emailaddres = EmailAddress.getText().toString();


                loginPreferences = getSharedPreferences("forgotpass", MODE_PRIVATE);
                loginPrefsEditor = loginPreferences.edit();
                String Ema = loginPreferences.getString("Email", "");
                YOURpassword = loginPreferences.getString("Password", "");


                if (Ema.equalsIgnoreCase(Emailaddres)) {
                    isLoggedIn = true;

                }


                if (isLoggedIn == false) {
                    Toast toast = Toast.makeText(ResetPasswordActivity.this, "invalid Email Address", Toast.LENGTH_LONG);//or LENGTH_short
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG); // or LENGTH_short
                    //  toast.setText("ya gmalhooooo");
                    toast.show();
                    Toast.makeText(ResetPasswordActivity.this, "invalid Email Address", Toast.LENGTH_SHORT).show();
                } else if (isLoggedIn == true) {

                    Random r = new Random();
                    int Low = 10000;
                    int High = 80000;
                    int Result = r.nextInt(High - Low) + Low;
                    codewhichGenerated = String.valueOf(Result);
                    //   String phoneNumber2=phoneNumber.getText().toString();
//                String  codeNumber2=codeNumber.getText().toStrin

                    //Creating SendMail object
                    SendMail sm = new SendMail(ResetPasswordActivity.this, Emailaddres, "About Forgotting Password", "please take this code and enter it in text in App ConnectFcis to " +
                            "get your password code is " + codewhichGenerated);

                    //Executing sendmail to send email
                    sm.execute();


                }


            }
        });

        receivePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String codeN = codeNumber.getText().toString();
                if (codeN.equalsIgnoreCase(codewhichGenerated)) {

                    setPassword.setText("Your Password is >>" + "\n" + YOURpassword);
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Failed Code Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}