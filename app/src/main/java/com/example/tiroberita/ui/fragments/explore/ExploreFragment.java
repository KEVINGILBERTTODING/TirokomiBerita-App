package com.example.tiroberita.ui.fragments.explore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiroberita.R;
import com.example.tiroberita.databinding.FragmentExploreBinding;

public class ExploreFragment extends Fragment {
    private FragmentExploreBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(inflater, container, false);


        init();
        return binding.getRoot();
    }

    private void init() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
    }

    private void listener() {
        binding.rlTerbaru.setOnClickListener(view -> {
            moveFragment(new TerbaruFragment());
        });

        binding.rlEkonomi.setOnClickListener(view -> {
            moveFragment(new EkonomiFragment());
        });

    }

    private void moveFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, fragment)
                .addToBackStack(null).commit();
    }
}