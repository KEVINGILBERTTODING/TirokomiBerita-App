package com.example.tiroberita.ui.activities.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.tiroberita.R;
import com.example.tiroberita.databinding.ActivityMainBinding;
import com.example.tiroberita.model.AppModel;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.ui.fragments.redactions.antara.HomeAntaraFragment;
import com.example.tiroberita.ui.fragments.redactions.cnbc.HomeCnbcFragment;
import com.example.tiroberita.ui.fragments.redactions.cnn.HomeCnnFragment;
import com.example.tiroberita.ui.fragments.post.SavePostFragment;
import com.example.tiroberita.ui.fragments.redactions.jpnn.HomeJpnnFragment;
import com.example.tiroberita.ui.fragments.redactions.kumparan.HomeKumparanFragment;
import com.example.tiroberita.ui.fragments.redactions.okezone.HomeOkezoneFragment;
import com.example.tiroberita.ui.fragments.redactions.republika.HomeRepublikaFragment;
import com.example.tiroberita.ui.fragments.redactions.sindonews.HomeSindoNewsFragment;
import com.example.tiroberita.ui.fragments.redactions.suara.HomeSuaraFragment;
import com.example.tiroberita.ui.fragments.redactions.tempo.HomeTempoFragment;
import com.example.tiroberita.ui.fragments.redactions.tribun.HomeTribunNewsFragment;
import com.example.tiroberita.ui.fragments.setting.SettingFragment;
import com.example.tiroberita.util.constans.Constans;
import com.example.tiroberita.util.constans.RedactionConstans;
import com.example.tiroberita.viewmodel.app.AppViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import me.ibrahimsn.lib.OnItemSelectedListener;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ActivityMainBinding binding;
    private String redactionFav, urlUpdate;
    private AppViewModel appViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        listener();

        // check app version
        checkUpdate();


        validateRedactionFav();
    }

    private void listener() {

        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        validateRedactionFav();
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

        binding.btnUpdate.setOnClickListener(view -> {
            directPost(urlUpdate);
        });





    }

    private void init() {
        sharedPreferences = getSharedPreferences(Constans.SHARED_PREF_NAME, MODE_PRIVATE);
        redactionFav = sharedPreferences.getString(Constans.REDACTION_FAVORIT, null);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

    }

    private void validateRedactionFav() {
        if (redactionFav.equals(RedactionConstans.CNN)) {
            moveFragment(new HomeCnnFragment());
        }else if (redactionFav.equals(RedactionConstans.TRIBUN)) {
            moveFragment(new HomeTribunNewsFragment());
        }else if (redactionFav.equals(RedactionConstans.KUMPARAN)) {
            moveFragment(new HomeKumparanFragment());
        }else if (redactionFav.equals(RedactionConstans.OKEZONE)) {
            moveFragment(new HomeOkezoneFragment());
        }else if (redactionFav.equals(RedactionConstans.CNBC)) {
            moveFragment(new HomeCnbcFragment());
        }else if (redactionFav.equals(RedactionConstans.ANTARA)) {
            moveFragment(new HomeAntaraFragment());
        }else if (redactionFav.equals(RedactionConstans.TEMPO)) {
            moveFragment(new HomeTempoFragment());
        }else if (redactionFav.equals(RedactionConstans.SINDONEWS)) {
            moveFragment(new HomeSindoNewsFragment());
        }else if (redactionFav.equals(RedactionConstans.REPUBLIKA)) {
            moveFragment(new HomeRepublikaFragment());
        }else if (redactionFav.equals(RedactionConstans.SUARA)) {
            moveFragment(new HomeSuaraFragment());
        }else if (redactionFav.equals(RedactionConstans.JPNN)) {
            moveFragment(new HomeJpnnFragment());
        }

    }

    private void directPost(String postUrl) {
        // buka browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(postUrl));
        startActivity(browserIntent);
    }

    private void checkUpdate() {
        appViewModel.checkUpdate().observe(this, new Observer<FirebaseResponseModel<AppModel>>() {
            @Override
            public void onChanged(FirebaseResponseModel<AppModel> appModelFirebaseResponseModel) {
                if (appModelFirebaseResponseModel.getSuccess() == true && appModelFirebaseResponseModel.getData().getVersion() != null) {
                    if (!Constans.APP_VER.equals(appModelFirebaseResponseModel.getData().getVersion())) {
                        binding.rlBottomSheetUpdate.setVisibility(View.VISIBLE);
                        binding.vOverlay.setVisibility(View.VISIBLE);
                        binding.frameHome.setVisibility(View.GONE);
                        binding.tvDescUpdate.setText(appModelFirebaseResponseModel.getData().getDescription());
                        urlUpdate = appModelFirebaseResponseModel.getData().getUrl();
                    }
                }
            }
        });
    }



    private void moveFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, fragment).commit();
    }
}