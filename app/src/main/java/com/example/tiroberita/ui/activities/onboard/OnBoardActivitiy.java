package com.example.tiroberita.ui.activities.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.tiroberita.R;
import com.example.tiroberita.ui.fragments.onboard.OnBoardFragment;

public class OnBoardActivitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board_activitiy);

        moveFragment(new OnBoardFragment());
    }

    private void moveFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameOnboard, fragment).commit();
    }
}