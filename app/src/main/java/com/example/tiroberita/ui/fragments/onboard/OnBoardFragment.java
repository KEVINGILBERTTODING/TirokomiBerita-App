package com.example.tiroberita.ui.fragments.onboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiroberita.R;
import com.example.tiroberita.databinding.FragmentOnBoardBinding;

public class OnBoardFragment extends Fragment {

    private FragmentOnBoardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
    }

    private void listener() {
        binding.btnNext.setOnClickListener(view -> {
            moveFragment(new RedactionsPickerFragment());
        });
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