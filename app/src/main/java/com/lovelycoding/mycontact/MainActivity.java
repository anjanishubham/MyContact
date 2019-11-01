package com.lovelycoding.mycontact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;

import com.lovelycoding.mycontact.ui.main.MainFragment;


public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mToolbar = findViewById(R.id.toolbar);
        initToolbar();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("My Contact");
        mToolbar.setTitleTextColor(Color.WHITE);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
