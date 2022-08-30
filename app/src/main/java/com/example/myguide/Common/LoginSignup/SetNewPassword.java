package com.example.myguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myguide.Databases.CheckInternet;
import com.example.myguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SetNewPassword extends AppCompatActivity {

//    Variables
    TextInputLayout password, confirmPass;
    RelativeLayout progressBar;
    Button update_btn;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);

        password = findViewById(R.id.password1);
        confirmPass = findViewById(R.id.password2);
        progressBar = findViewById(R.id.password_progress_bar);
        update_btn = findViewById(R.id.set_new_pw_button);

        //        Animations Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.side_anim);

        //Set animation to all element
        password.setAnimation(animation);
        confirmPass.setAnimation(animation);
        progressBar.setAnimation(animation);
        update_btn.setAnimation(animation);

    }


    public void setNewPassword(View view) {
        //Check Internet
        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)) {
            showCustomDialog();
        }

//        Check phone Number
        if (!validatePassword() | !validateConfirmPass()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

//        Get data field

        String _password = password.getEditText().getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("phoneNo");

//        Database

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNumber).child("password").setValue(_password);

        startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
        finish();

    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
        builder.setMessage("Please connect to the Internet to procees further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
                        finish();
                    }
                });

    }


    private boolean validatePassword() {

        String _password = password.getEditText().getText().toString().trim();


        if (_password.isEmpty()) {
            password.setError("Phone number can not be empty");
            password.requestFocus();
            return false;
        } else {
        return true;}
    }

    private boolean validateConfirmPass(){
        String _confirmPass = confirmPass.getEditText().getText().toString().trim();
        if (_confirmPass.isEmpty()) {
            confirmPass.setError("Password can not be empty");
            confirmPass.requestFocus();
            return false;
        }else
        {
            return true;
        }
    }


}