package com.lovelycoding.mycontact.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lovelycoding.mycontact.MainActivity;
import com.lovelycoding.mycontact.R;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
}
