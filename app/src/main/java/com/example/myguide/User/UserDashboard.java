package com.example.myguide.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myguide.Common.LoginSignup.RetailerStartUpScreen;
import com.example.myguide.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.myguide.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.example.myguide.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //    Variables
    static final float END_SCALE = 0.7f;
    RecyclerView featuredRecycler, mostViewRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menuIcon;
    LinearLayout contentView;

    //    Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_usersdashboard);

//        Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
//        loginSignupBtn = findViewById(R.id.login_signup)


//        Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

//        Navigation Drawer


        navigationDrawer();

//        Recycler Views Function Calls
        featuredRecycler();
        mostViewRecycler();
        categoriesRecycler();

    }


//    Navigation Drawer Functions

    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {

                    drawerLayout.closeDrawer(GravityCompat.START);

                } else drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {


        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

//                Scale the view based on current slide offset
                final float diffScaleOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaleOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

//                Translate the view, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaleOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }


            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_all_categories:
                startActivity(new Intent(getApplicationContext(), AllCategories.class));
                break;
        }
        return true;
    }

//    Recycler Views Function

    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.mc_donal, "McDonal's", "It's very baddddddddd......."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city_1, "Edenrobe", "It's very baddddddddd......."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city_2, "Sweet and Bakers", "It's very baddddddddd......."));
//        featuredLocations.add(new FeaturedHelperClass(R.drawable.gradient1, "Sweet and Bakers", "It's very baddddddddd......."));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);


        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }

    private void mostViewRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.mc_donal, "McDonal's", "It's very baddddddddd......."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city_1, "Edenrobe", "It's very baddddddddd......."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city_2, "Sweet and Bakers", "It's very baddddddddd......."));
//        featuredLocations.add(new FeaturedHelperClass(R.drawable.gradient1, "Sweet and Bakers", "It's very baddddddddd......."));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);


        GradientDrawable gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
    }

    private void categoriesRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.mc_donal, "McDonal's", "It's very baddddddddd......."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city_1, "Edenrobe", "It's very baddddddddd......."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.city_2, "Sweet and Bakers", "It's very baddddddddd......."));
//        featuredLocations.add(new FeaturedHelperClass(R.drawable.gradient1, "Sweet and Bakers", "It's very baddddddddd......."));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);


        GradientDrawable gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }

//    Normal function


    public void callRetailerScreen(View view) {

        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class ));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();

    }


}