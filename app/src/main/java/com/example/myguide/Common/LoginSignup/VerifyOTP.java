package com.example.myguide.Common.LoginSignup;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.myguide.Databases.UserHelperClass;
import com.example.myguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Verify;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.HttpCookie;
import java.util.concurrent.TimeUnit;

public class VerifyOTP<FirebaseTooManyRequestsException> extends AppCompatActivity {

    //    Variable
    PinView pinFromUser;
    ImageView close;
    TextView code, otpDescription;
    Button verify;
    String codeBySystem;
    private FirebaseAuth mAuth;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    String fullName, username, password, date, gender, phoneNo, whatToDo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_otp);

//        Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

//        hooks

        close = findViewById(R.id.close_image);
        code = findViewById(R.id.code);
        verify = findViewById(R.id.verify_button);

        pinFromUser = findViewById(R.id.pin_view);
        otpDescription = findViewById(R.id.otp_description);

//Get all the data from intent
        fullName = getIntent().getStringExtra("fullName");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");
        phoneNo = getIntent().getStringExtra("phoneNo");
        whatToDo = getIntent().getStringExtra("WhatToDo");


        otpDescription.setText("Enter One Time Password Sent On" + phoneNo);
        sendVerificationCodeToUser(phoneNo);

    }


    private void sendVerificationCodeToUser(String phoneNo) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId, token);
            codeBySystem = verificationId;

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                pinFromUser.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("ABCDEF...........", String.valueOf(task.isSuccessful()));
                if (task.isSuccessful()) {
                    Log.i("Xin chao Viet Nam", String.valueOf(whatToDo.equals("updateData")));
                    if(whatToDo.equals("updateData")){
                        updateOldUsersData();
                    }else{
                        storeNewUsersData();
                    }

                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void updateOldUsersData() {

        Intent intent = new Intent(getApplicationContext(), SetNewPassword.class);
        intent.putExtra("phoneNo", phoneNo);
        startActivity(intent);
        finish();

    }

    private void storeNewUsersData() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        DatabaseReference myRef2 = database.getReference("Hello");
        UserHelperClass addNewUser = new UserHelperClass(fullName, username, phoneNo, password, date, gender);
        myRef.child(phoneNo).setValue(addNewUser);
        myRef2.setValue("Hello  TMT  !!");
    }

    //
    public void clickNextScreenFromOTP(View view) {

        String code = pinFromUser.getText().toString();
        if (code.isEmpty()) {
            verifyCode(code);
        }
        startActivity(new Intent(getApplicationContext(), SetNewPassword.class));
        finish();
    }

}