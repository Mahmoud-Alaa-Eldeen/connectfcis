package com.khaledahmed.connectfcis;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private ImageView official_logo;
    private ProgressBar mainProgressBar;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            public void run() {
                // Update the progress bar
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                }, 3000);
            }
        }).start();

//        official_logo = (ImageView) findViewById(R.id.official_logo);
//        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
//        intent = new Intent(getApplicationContext(), LoginActivity.class);
//        official_logo.startAnimation(animation);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                finish();
//                startActivity(intent);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }
}
