package com.example.tiroberita.ui.activities.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tiroberita.R;
import com.example.tiroberita.databinding.ActivityMainBinding;
import com.example.tiroberita.ui.fragments.home.HomeCnnFragment;
import com.example.tiroberita.ui.fragments.home.HomeFragment;
import com.example.tiroberita.ui.fragments.post.SavePostFragment;
import com.google.android.material.navigation.NavigationBarView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        moveFragment(new HomeCnnFragment());

        listener();
    }

    private void listener() {
        binding.bottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    moveFragment(new HomeCnnFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuSave) {
                    moveFragment(new SavePostFragment());
                    return true;
                }
                return false;
            }
        });
    }

    private void moveFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, fragment).commit();
    }
}