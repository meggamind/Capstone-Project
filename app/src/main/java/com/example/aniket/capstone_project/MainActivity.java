package com.example.aniket.capstone_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.aniket.capstone_project.ui.login.SignInActivity;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}
