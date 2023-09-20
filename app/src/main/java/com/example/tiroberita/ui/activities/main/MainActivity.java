package com.example.tiroberita.ui.activities.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tiroberita.R;
import com.example.tiroberita.databinding.ActivityMainBinding;
import com.example.tiroberita.ui.fragments.redactions.cnn.HomeCnnFragment;
import com.example.tiroberita.ui.fragments.post.SavePostFragment;
import com.example.tiroberita.ui.fragments.setting.SettingFragment;
import com.google.android.material.navigation.NavigationBarView;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;
import me.ibrahimsn.lib.BottomBarItem;
import me.ibrahimsn.lib.OnItemSelectedListener;

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

        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        moveFragment(new HomeCnnFragment());
                        break;
                    case 1:
                        moveFragment(new SavePostFragment());
                        break;
                    case 2:
                        moveFragment(new SettingFragment());
                        break;
                }
                return false;
            }
        });





    }

    private void moveFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, fragment).commit();
    }
}