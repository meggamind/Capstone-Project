package com.example.aniket.capstone_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.aniket.capstone_project.data.explore.ThingsToDoConstants;
import com.example.aniket.capstone_project.ui.explore.ExploreActivity;
import com.example.aniket.capstone_project.ui.login.LoginActivity;
import com.example.aniket.capstone_project.ui.login.SignInActivity;
import com.example.aniket.capstone_project.ui.userprofile.UserProfileActivity;
import com.example.aniket.capstone_project.utils.view.ScreenUI;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_main);


//        Intent intent = new Intent(MainActivity.this, ExploreActivity.class);
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.action_explore:
//                                Intent exploreIntent = new Intent(MainActivity.this, ExploreActivity.class);
//                                startActivity(exploreIntent);
//                                break;
//                            case R.id.action_upload:
//
//                            case R.id.action_profile:
//                                Intent profileIntent = new Intent(MainActivity.this, UserProfileActivity.class);
//                                int[] startingLocation = new int[2];
////                                view.getLocationOnScreen(startingLocation);
//                                startingLocation[0] += ScreenUI.getScreenWidth(getApplicationContext()) / 2;
//                                profileIntent.putExtra(ThingsToDoConstants.ARG_REVEAL_START_LOCATION, startingLocation);
//
//                                startActivity(profileIntent);
//                        }
//                        return true;
//                    }
//                });


    }


}
