package com.example.myguide.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myguide.R;
import com.example.myguide.User.UserDashboard;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIMER = 5000;


    //    Variables
    ImageView backgroundImage;
    TextView poweredByLine;

    //    Animation
    Animation sideAnima, bottomAnima;
    SharedPreferences onBoardingScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        // Write a message to the database


//        Hooks
        backgroundImage = findViewById(R.id.background_image);
        poweredByLine = findViewById(R.id.power_by_line);

//        Animations
        sideAnima = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnima = AnimationUtils.loadAnimation(this, R.anim.bottom_anima);


//        set Animation on elements
        backgroundImage.setAnimation(sideAnima);
        poweredByLine.setAnimation(bottomAnima);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);
                Log.i("Helloworld", String.valueOf(isFirstTime));

                if (isFirstTime) {

                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_TIMER);
    }
}