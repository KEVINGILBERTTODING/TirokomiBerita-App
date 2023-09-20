package com.example.tiroberita.ui.fragments.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiroberita.databinding.FragmentSettingBinding;
import com.example.tiroberita.ui.activities.onboard.OnBoardActivitiy;
import com.example.tiroberita.util.constans.Constans;


public class SettingFragment extends Fragment {


    private FragmentSettingBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
        init();
    }

    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    private void listener() {
        binding.btnLogout.setOnClickListener(view -> {
            deleteSharedPref();
        });
    }

    private void deleteSharedPref() {
        editor.clear();
        editor.apply();
        startActivity(new Intent(getActivity(), OnBoardActivitiy.class));
        getActivity().finish();
    }
}