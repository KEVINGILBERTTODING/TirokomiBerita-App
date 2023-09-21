package com.example.tiroberita.ui.fragments.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiroberita.databinding.FragmentSettingBinding;
import com.example.tiroberita.model.AppModel;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.ui.activities.onboard.OnBoardActivitiy;
import com.example.tiroberita.util.constans.Constans;
import com.example.tiroberita.viewmodel.app.AppViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class SettingFragment extends Fragment {


    private FragmentSettingBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AppViewModel appViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
        checkUpdate();

    }

    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

    }

    private void checkUpdate() {
        appViewModel.checkUpdate().observe(getViewLifecycleOwner(), new Observer<FirebaseResponseModel<AppModel>>() {
            @Override
            public void onChanged(FirebaseResponseModel<AppModel> appModelFirebaseResponseModel) {
                if (appModelFirebaseResponseModel.getSuccess() == true) {
                    showToast(Constans.TOAST_NORMAL, appModelFirebaseResponseModel.getData().getTitle());
                }else {
                    showToast(Constans.TOAST_ERROR, appModelFirebaseResponseModel.getMessage());
                }
            }
        });
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

    private void showToast(String type, String message) {
        if (type.equals(Constans.TOAST_SUCCESS)){
            Toasty.success(getContext(), message, Toasty.LENGTH_LONG).show();
        }else if (type.equals(Constans.TOAST_NORMAL)){
            Toasty.normal(getContext(), message, Toasty.LENGTH_LONG).show();
        }else {
            Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();

        }
    }

}