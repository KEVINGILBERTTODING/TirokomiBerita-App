package com.example.tiroberita.ui.fragments.onboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tiroberita.R;
import com.example.tiroberita.databinding.FragmentOnBoardBinding;
import com.example.tiroberita.util.Constans;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import es.dmoral.toasty.Toasty;

public class OnBoardFragment extends Fragment {

    private FragmentOnBoardBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardBinding.inflate(inflater, container, false);

        init();
        setUpBottomSheet();
        validateNewUser();

        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
    }

    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    private void listener() {
        binding.btnNext.setOnClickListener(view -> {
            showBottomSheet();
        });
        binding.vOverlay.setOnClickListener(view -> {
            hideBottomSheet();
        });
        binding.btnSimpan.setOnClickListener(view -> {
            saveUsername();
        });

    }

    private void validateNewUser() {
        if (sharedPreferences.getString(Constans.USERNAME, null) != null){
            moveFragment(new RedactionsPickerFragment());
        }
    }


    private void setUpBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.rlBottomSheet);
        // set behaviior
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    hideBottomSheet();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    private void showBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(600);
        binding.vOverlay.setVisibility(View.VISIBLE);
    }


    private void hideBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.vOverlay.setVisibility(View.GONE);
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

    private void saveUsername() {
        if (binding.etUsername.getText().toString().isEmpty()) {
            showToast(Constans.TOAST_NORMAL, "Username tidak boleh kosong");
        }else if (binding.etUsername.getText().length() > 20){
            showToast(Constans.TOAST_NORMAL, "Tidak boleh lebih dari 20 karakter");
        }else {
            editor.putString(Constans.USERNAME, binding.etUsername.getText().toString());
            editor.apply();
            moveFragment(new RedactionsPickerFragment());
        }

    }
    private void moveFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameOnboard, fragment).commit();
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}