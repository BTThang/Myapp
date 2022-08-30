package com.example.myguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myguide.R;

import java.util.Calendar;

public class SignUp2ndClass extends AppCompatActivity {

//    Variables

    ImageView backBtn;
    Button next, login;
    TextView titleText;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2nd_class);

//        Hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup22_next_button);
        login = findViewById(R.id.signup22_login_button);
        titleText = findViewById(R.id.signup_title_text);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.date_picker);


    }

    public void call3rdSignUpScreen(View view) {
        if(!validateAge() | !validateGender())
        {
            return;
        }

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String gender = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String date = day+"/"+month+"/"+year;
        String _fullName = getIntent().getStringExtra("fullName");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");

        Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);

//        Add transition

        intent.putExtra("date", date);
        intent.putExtra("gender", gender);
        intent.putExtra("fullName", _fullName);
        intent.putExtra("username", _username);
        intent.putExtra("password", _password);


        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_btn");
        pairs[1] = new Pair<View, String>(next, "signup_next_button");
        pairs[2] = new Pair<View, String>(login, "signup_login_button");
        pairs[3] = new Pair<View, String>(radioGroup, "Radio_group");
        pairs[4] = new Pair<View, String>(datePicker, "datePicker");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {

            Toast.makeText(this, "Please select gender", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }


    private boolean validateAge() {

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "You are eligible to apply", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}