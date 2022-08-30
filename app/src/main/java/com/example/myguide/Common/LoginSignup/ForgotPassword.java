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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myguide.Databases.CheckInternet;
import com.example.myguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgotPassword extends AppCompatActivity {

    //    Variable
    private ImageView screenIcon;
    private TextView title, description;
    private TextInputLayout phoneNumberText;
    private CountryCodePicker countryCodePicker;
    private Button button;
    private Animation animation;
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);

        // Hooks
        screenIcon = findViewById(R.id.forget_icon_pass);
        title = findViewById(R.id.title_forgot);
        description = findViewById(R.id.descrip_forget);
        phoneNumberText = findViewById(R.id.forget_phone_number);
        countryCodePicker = findViewById(R.id.country_code_picker);
        button = findViewById(R.id.next_btn);
        progressBar = findViewById(R.id.progress_bar);

        //        Animations Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.side_anim);

        //Set animation to all element
        screenIcon.setAnimation(animation);
        title.setAnimation(animation);
        description.setAnimation(animation);
        phoneNumberText.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
        button.setAnimation(animation);

    }

    public void verifyPhoneNumber(View view){

        //Check Internet
        CheckInternet checkInternet = new CheckInternet();
        if(!checkInternet.isConnected(this)){
            showCustomDialog();
        }

//        Check phone Number
        if(!validateFields()){
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

//        Get data field

        String _phoneNumber = phoneNumberText.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }

        final String _completePhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + _phoneNumber;

//        Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phoneNumberText.setError(null);
                    phoneNumberText.setErrorEnabled(false);
                    Intent intent = new Intent(getApplicationContext(),VerifyOTP.class);
                    intent.putExtra("phoneNo", _completePhoneNumber);
                    intent.putExtra("whatToDo", "updateData");
                    startActivity(intent);
                    finish();

                    progressBar.setVisibility(View.GONE);
                }else
                {
                    progressBar.setVisibility(View.GONE);
                    phoneNumberText.setError("No such user exit");
                    phoneNumberText.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ForgotPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        }


    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
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
                        startActivity(new Intent(getApplicationContext(), SetNewPassword.class));
                        finish();
                    }
                });

    }

    private boolean isConnected(Login login) {

        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn) != null && wifiConn.isConnected() || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateFields() {

        String _phoneNumber = phoneNumberText.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumberText.setError("Phone number can not be empty");
            phoneNumberText.requestFocus();
            return false;
        }
        return true;
    }


}