package com.example.myguide.LocationOwner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myguide.Databases.SessionManager;
import com.example.myguide.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;

public class RetailerDashboard extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_dashboard);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_dashboard, true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RetailerDashboardFragment()).commit();
        bottomMenu();

//        TextView textView = findViewById(R.id.textView);
//
//        SessionManager sessionManager = new SessionManager(RetailerDashboard.this, SessionManager.SESSION_USERSESSION );
//        HashMap<String, String> usersDetails = sessionManager.getUsersDetailFromSession();
//
//        String fullName = usersDetails.get(SessionManager.KEY_FULLNAME);
//        String phoneNumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);
//        String username = usersDetails.get(SessionManager.KEY_USERNAME);
//        String password = usersDetails.get(SessionManager.KEY_PASSWORD);
//        String date = usersDetails.get(SessionManager.KEY_DATE);
//        String gender = usersDetails.get(SessionManager.KEY_GENDER);
//
//        textView.setText(fullName + "\n" + phoneNumber + "\n" + username + "\n" + password + "\n" + date + "\n" + gender);
    }

    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.bottom_nav_dashboard:
                        fragment = new RetailerDashboardFragment();
                        break;
                    case R.id.bottom_nav_manager:
                        fragment = new RetailerManagerFragment();
                        break;
                    case R.id.bottom_nav_profile:
                        fragment = new RetailerProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });



    }
}