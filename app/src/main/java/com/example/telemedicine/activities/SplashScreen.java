package com.example.telemedicine.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.telemedicine.R;

public class SplashScreen extends AppCompatActivity
{
SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences=this.getSharedPreferences("person",this.MODE_PRIVATE);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        loadApp();
    }

    private void loadApp()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(SplashScreen.this, WelcomeScreen.class);
                startActivity(intent);
                finishAffinity();
            }
        }, 1000);
    }
}
