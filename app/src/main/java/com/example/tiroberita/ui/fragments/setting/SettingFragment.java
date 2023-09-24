package com.example.tiroberita.ui.fragments.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.adapters.AbsSpinnerBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiroberita.R;
import com.example.tiroberita.databinding.FragmentSettingBinding;
import com.example.tiroberita.model.AppModel;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.ui.activities.onboard.OnBoardActivitiy;
import com.example.tiroberita.ui.fragments.redactions.cnbc.HomeCnbcFragment;
import com.example.tiroberita.ui.fragments.redactions.cnn.HomeCnnFragment;
import com.example.tiroberita.ui.fragments.redactions.jpnn.HomeJpnnFragment;
import com.example.tiroberita.ui.fragments.redactions.kumparan.HomeKumparanFragment;
import com.example.tiroberita.ui.fragments.redactions.okezone.HomeOkezoneFragment;
import com.example.tiroberita.ui.fragments.redactions.republika.HomeRepublikaFragment;
import com.example.tiroberita.ui.fragments.redactions.sindonews.HomeSindoNewsFragment;
import com.example.tiroberita.ui.fragments.redactions.suara.HomeSuaraFragment;
import com.example.tiroberita.ui.fragments.redactions.tempo.HomeTempoFragment;
import com.example.tiroberita.ui.fragments.redactions.tribun.HomeTribunNewsFragment;
import com.example.tiroberita.util.constans.Constans;
import com.example.tiroberita.util.constans.RedactionConstans;
import com.example.tiroberita.viewmodel.app.AppViewModel;
import com.example.tiroberita.viewmodel.auth.AuthViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class SettingFragment extends Fragment {


    private FragmentSettingBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AppViewModel appViewModel;
    private AuthViewModel authViewModel;
    private String username, userId, postUrl, mediaFavorite;
    private BottomSheetBehavior bottomSheetBehavior, bottomSheetCheckUpdate, bottomSheetUpdateUsername, bottomSheetMediaPicker, bottomSheetAboutUs;


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
        setUpBottomSheetMenu();
        setUpBottomSheetCheckUpdate();
        setUpBottomSheetMediaPicker();
        setUpBottomSheetUpdateUsername();
        setUpBottomSheetAboutUs();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetCheckUpdate.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetUpdateUsername.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetMediaPicker.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetAboutUs.setState(BottomSheetBehavior.STATE_HIDDEN);

        binding.tvUsername.setText("Hai, " + username);





    }

    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        userId = sharedPreferences.getString(Constans.USER_ID, "0");
        username = sharedPreferences.getString(Constans.USERNAME, "none");
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);


    }

    private void checkUpdate() {
        appViewModel.checkUpdate().observe(getViewLifecycleOwner(), new Observer<FirebaseResponseModel<AppModel>>() {
            @Override
            public void onChanged(FirebaseResponseModel<AppModel> appModelFirebaseResponseModel) {
                if (appModelFirebaseResponseModel.getSuccess() == true && appModelFirebaseResponseModel.getData() != null) {
                  if (!appModelFirebaseResponseModel.getData().getVersion().contains(Constans.APP_VER)) {
                      showBottomSheetCheckUpdate();
                      binding.tvDescUpdate.setText(appModelFirebaseResponseModel.getData().getTitle());
                      postUrl = appModelFirebaseResponseModel.getData().getUrl();
                  }else {
                      showToast(Constans.TOAST_NORMAL, "Anda telah menggunakan versi terbaru");
                  }
                }else {
                    showToast(Constans.TOAST_ERROR, Constans.ERR_MESSAGE);
                }
            }
        });
    }

    private void listener() {
        binding.btnLogout.setOnClickListener(view -> {
           showBottomSheetMenu();
        });

        binding.vOverlay.setOnClickListener(view -> {
            hideBottomSheet();
        });
        binding.vOverlay2.setOnClickListener(view -> {
            hideBottomSheetCheckUpdate();
        });

        binding.vOverlay3.setOnClickListener(view -> {
            hideBottomSheetUpdateUsername();
            hideBottomSheetMediaPicker();
            hideBottomSheetAboutUs();
        });

        binding.cvUpdateApp.setOnClickListener(view -> {
            hideBottomSheet();
            checkUpdate();
        });

        binding.btnUpdate.setOnClickListener(view -> {
            directPost(postUrl);
        });

        binding.cvUpdateUsername.setOnClickListener(view -> {
            hideBottomSheet();
            showBottomSheetUpdateUsername();
        });

        binding.btnSimpan.setOnClickListener(view -> {
            updateUsername();
        });

        binding.cvMediaPicker.setOnClickListener(view -> {
            showBottomSheetMediaPicker();
            hideBottomSheet();
        });

        binding.btnPilih.setOnClickListener(view -> {
            updateMediaFavorit();
        });
        binding.cvAboutUs.setOnClickListener(view -> {
            showBottomSheetAboutUs();
            hideBottomSheet();

        });

        binding.mnuKumparan.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.KUMPARAN;
            binding.btnPilih.setEnabled(true);
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        });

        binding.mnuAntara.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.ANTARA;
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));

        });

        binding.mnuTribun.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.TRIBUN;
            binding.btnPilih.setEnabled(true);
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));

        });

        binding.mnuCnbc.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.CNBC;
            binding.btnPilih.setEnabled(true);
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        });

        binding.mnuOkezone.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.OKEZONE;
            binding.btnPilih.setEnabled(true);
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        });

        binding.mnuCnn.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.CNN;
            binding.btnPilih.setEnabled(true);
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        });

        binding.mnuSindoNews.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.SINDONEWS;
            binding.btnPilih.setEnabled(true);
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));


        });

        binding.mnuTempo.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.TEMPO;
            binding.btnPilih.setEnabled(true);
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        });

        binding.mnuSuara.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.SUARA;
            binding.btnPilih.setEnabled(true);
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        });

        binding.mnuRepublika.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.REPUBLIKA;
            binding.btnPilih.setEnabled(true);
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        });

        binding.mnuJpnn.setOnClickListener(view -> {
            mediaFavorite = RedactionConstans.JPNN;
            binding.btnPilih.setEnabled(true);
            binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
            binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded_stroke));
        });
    }


    private void updateUsername() {
        if (binding.etUsername.getText().toString().isEmpty()) {
            showToast(Constans.TOAST_ERROR, "Username tidak boleh kosong");

        }else if (userId == null){
            showToast(Constans.TOAST_ERROR, Constans.ERR_MESSAGE);
        }else {
            authViewModel.updateUsername(binding.etUsername.getText().toString(), userId).observe(getViewLifecycleOwner(), new Observer<FirebaseResponseModel>() {
                @Override
                public void onChanged(FirebaseResponseModel firebaseResponseModel) {
                    if (firebaseResponseModel.getSuccess() == true) {
                        showToast(Constans.TOAST_NORMAL, firebaseResponseModel.getMessage());
                        editSharedPref(Constans.USERNAME, binding.etUsername.getText().toString());
                        binding.tvUsername.setText(binding.etUsername.getText().toString());
                        hideBottomSheetUpdateUsername();

                    }else {
                        showToast(Constans.TOAST_ERROR, firebaseResponseModel.getMessage());
                    }
                }
            });
        }

    }


    private void updateMediaFavorit() {
        if (userId != null && mediaFavorite != null) {
            authViewModel.updateMediaFavorit(userId, mediaFavorite).observe(getViewLifecycleOwner(), new Observer<FirebaseResponseModel>() {
                @Override
                public void onChanged(FirebaseResponseModel firebaseResponseModel) {
                    if (firebaseResponseModel.getSuccess() == true) {
                        showToast(Constans.TOAST_NORMAL, firebaseResponseModel.getMessage());
                        hideBottomSheetMediaPicker();
                        editSharedPref(Constans.REDACTION_FAVORIT,  mediaFavorite);
                        binding.vOverlay3.setVisibility(View.GONE);
                    }else {
                        showToast(Constans.TOAST_ERROR, firebaseResponseModel.getMessage());

                    }
                }
            });
        }else {
            showToast(Constans.TOAST_ERROR, Constans.ERR_MESSAGE);
        }

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

    private void directPost(String postUrl) {
        // buka browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(postUrl));
        startActivity(browserIntent);
    }



    private void setUpBottomSheetMenu() {

        bottomSheetBehavior = BottomSheetBehavior.from(binding.rlBottomSheetMenu);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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

    private void setUpBottomSheetCheckUpdate() {

        bottomSheetCheckUpdate = BottomSheetBehavior.from(binding.rlBottomSheetUpdate);
        bottomSheetCheckUpdate.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetCheckUpdate.setHideable(true);

        bottomSheetCheckUpdate.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    hideBottomSheetCheckUpdate();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    private void setUpBottomSheetMediaPicker() {

        bottomSheetMediaPicker = BottomSheetBehavior.from(binding.rlBottomSheetMediaPicker);
        bottomSheetMediaPicker.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetMediaPicker.setHideable(true);

        bottomSheetMediaPicker.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    hideBottomSheetMediaPicker();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }


    private void setUpBottomSheetUpdateUsername() {

        bottomSheetUpdateUsername = BottomSheetBehavior.from(binding.rlBottomSheetUpdateUsername);
        bottomSheetUpdateUsername.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetUpdateUsername.setHideable(true);

        bottomSheetUpdateUsername.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    hideBottomSheetUpdateUsername();


                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }


    private void setUpBottomSheetAboutUs() {

        bottomSheetAboutUs = BottomSheetBehavior.from(binding.rlBottomSheetAboutUs);
        bottomSheetAboutUs.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetAboutUs.setHideable(true);

        bottomSheetAboutUs.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    hideBottomSheetAboutUs();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    private void showBottomSheetCheckUpdate() {
        bottomSheetCheckUpdate.setState(BottomSheetBehavior.STATE_COLLAPSED);
        binding.vOverlay2.setVisibility(View.VISIBLE);
    }

    private void showBottomSheetUpdateUsername() {
        bottomSheetUpdateUsername.setState(BottomSheetBehavior.STATE_COLLAPSED);
        binding.vOverlay3.setVisibility(View.VISIBLE);
        binding.etUsername.setText(username);
    }

    private void hideBottomSheetUpdateUsername() {
        bottomSheetUpdateUsername.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.vOverlay3.setVisibility(View.GONE);
        binding.etUsername.setText("");
    }

    private void showBottomSheetAboutUs() {
        bottomSheetAboutUs.setState(BottomSheetBehavior.STATE_COLLAPSED);
        binding.vOverlay3.setVisibility(View.VISIBLE);

    }

    private void hideBottomSheetAboutUs() {
        bottomSheetAboutUs.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.vOverlay3.setVisibility(View.GONE);

    }




    private void showBottomSheetMediaPicker() {
        bottomSheetMediaPicker.setState(BottomSheetBehavior.STATE_COLLAPSED);
        binding.vOverlay3.setVisibility(View.VISIBLE);
        binding.etUsername.setText(username);
    }

    private void hideBottomSheetMediaPicker() {
        bottomSheetMediaPicker.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.vOverlay3.setVisibility(View.GONE);
        binding.btnPilih.setEnabled(false);
        binding.mnuKumparan.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        binding.mnuAntara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        binding.mnuTribun.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        binding.mnuCnbc.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        binding.mnuOkezone.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        binding.mnuCnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        binding.mnuSindoNews.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        binding.mnuSuara.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        binding.mnuTempo.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        binding.mnuRepublika.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
        binding.mnuJpnn.setBackground(getContext().getDrawable(R.drawable.container_media_rounded));
    }


        private void showBottomSheetMenu() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        binding.vOverlay.setVisibility(View.VISIBLE);

    }

    private void hideBottomSheet() {
        binding.vOverlay.setVisibility(View.GONE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    private void hideBottomSheetCheckUpdate() {
        binding.vOverlay2.setVisibility(View.GONE);
        bottomSheetCheckUpdate.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    private void editSharedPref(String name, String value) {
        editor.putString(name, value);
        editor.apply();
    }


}