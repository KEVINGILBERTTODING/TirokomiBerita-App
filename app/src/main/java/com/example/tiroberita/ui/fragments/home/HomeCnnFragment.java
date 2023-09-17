package com.example.tiroberita.ui.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Calendar;

import com.example.tiroberita.R;
import com.example.tiroberita.databinding.FragmentHomeCnnBinding;
import com.example.tiroberita.util.Constans;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeCnnFragment extends Fragment {

    private FragmentHomeCnnBinding binding;
    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeCnnBinding.inflate(inflater, container, false);

        init();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listenerTabLayout();
        listener();

        binding.tvUsername.setText(sharedPreferences.getString(Constans.USERNAME, "-"));

    }

    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }




    private void listenerTabLayout() {
        binding.menuTerbaru.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInternasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGayaHidup.setTextColor(getContext().getColor(R.color.second_font));


            binding.lnTerbaru.setVisibility(View.VISIBLE);
            binding.lnNasional.setVisibility(View.GONE);
            binding.lnInternasional.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnGayaHidup.setVisibility(View.GONE);


        });

        binding.menuNasional.setOnClickListener(view -> {
            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInternasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGayaHidup.setTextColor(getContext().getColor(R.color.second_font));


            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNasional.setVisibility(View.VISIBLE);
            binding.lnInternasional.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnGayaHidup.setVisibility(View.GONE);
        });

        binding.menuInternasional.setOnClickListener(view -> {
            binding.tvTbInternasional.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGayaHidup.setTextColor(getContext().getColor(R.color.second_font));


            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNasional.setVisibility(View.GONE);
            binding.lnInternasional.setVisibility(View.VISIBLE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnGayaHidup.setVisibility(View.GONE);
        });

        binding.menuEkonomi.setOnClickListener(view -> {
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInternasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGayaHidup.setTextColor(getContext().getColor(R.color.second_font));


            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNasional.setVisibility(View.GONE);
            binding.lnInternasional.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.VISIBLE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnGayaHidup.setVisibility(View.GONE);
        });

        binding.menuOlahraga.setOnClickListener(view -> {
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInternasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGayaHidup.setTextColor(getContext().getColor(R.color.second_font));


            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNasional.setVisibility(View.GONE);
            binding.lnInternasional.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.VISIBLE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnGayaHidup.setVisibility(View.GONE);
        });

        binding.menuTeknologi.setOnClickListener(view -> {
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInternasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGayaHidup.setTextColor(getContext().getColor(R.color.second_font));

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNasional.setVisibility(View.GONE);
            binding.lnInternasional.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.VISIBLE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnGayaHidup.setVisibility(View.GONE);
        });


        binding.menuHiburan.setOnClickListener(view -> {
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInternasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGayaHidup.setTextColor(getContext().getColor(R.color.second_font));

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNasional.setVisibility(View.GONE);
            binding.lnInternasional.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.VISIBLE);
            binding.lnGayaHidup.setVisibility(View.GONE);
        });

        binding.menuGayaHidup.setOnClickListener(view -> {
            binding.tvTbGayaHidup.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInternasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNasional.setVisibility(View.GONE);
            binding.lnInternasional.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnGayaHidup.setVisibility(View.VISIBLE);
        });
    }


    private void listener() {
    }

    private void greetings() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 12 && hour < 15) {
            binding.tvGreeting.setText("Selamat Siang");
        } else if (hour >= 15 && hour < 18) {
            binding.tvGreeting.setText("Selamat Sore");
        } else if (hour >= 18 && hour < 24) {
            binding.tvGreeting.setText("Selamat Malam");
        } else {
            binding.tvGreeting.setText("Selamat Pagi");
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}