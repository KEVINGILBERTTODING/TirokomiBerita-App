package com.example.tiroberita.ui.activities.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.tiroberita.R;
import com.example.tiroberita.ui.fragments.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moveFragment(new HomeFragment());
    }

    private void moveFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, fragment).commit();
    }
}