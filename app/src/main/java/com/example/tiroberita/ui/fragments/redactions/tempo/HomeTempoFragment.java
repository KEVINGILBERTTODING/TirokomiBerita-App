package com.example.tiroberita.ui.fragments.redactions.tempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tiroberita.R;
import com.example.tiroberita.databinding.FragmentHomeTempoBinding;
import com.example.tiroberita.model.DataModel;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.model.PostModel;
import com.example.tiroberita.model.ResponseModel;
import com.example.tiroberita.model.SaveModel;
import com.example.tiroberita.ui.ItemClickListener;
import com.example.tiroberita.ui.adapters.NewsAdapter;
import com.example.tiroberita.ui.fragments.redactions.antara.HomeAntaraFragment;
import com.example.tiroberita.ui.fragments.redactions.cnbc.HomeCnbcFragment;
import com.example.tiroberita.ui.fragments.redactions.cnn.HomeCnnFragment;
import com.example.tiroberita.ui.fragments.redactions.jpnn.HomeJpnnFragment;
import com.example.tiroberita.ui.fragments.redactions.kumparan.HomeKumparanFragment;
import com.example.tiroberita.ui.fragments.redactions.okezone.HomeOkezoneFragment;
import com.example.tiroberita.ui.fragments.redactions.republika.HomeRepublikaFragment;
import com.example.tiroberita.ui.fragments.redactions.sindonews.HomeSindoNewsFragment;
import com.example.tiroberita.ui.fragments.redactions.suara.HomeSuaraFragment;
import com.example.tiroberita.ui.fragments.redactions.tribun.HomeTribunNewsFragment;
import com.example.tiroberita.util.constans.Constans;
import com.example.tiroberita.viewmodel.post.PostViewModel;
import com.example.tiroberita.viewmodel.tempo.TempoViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class HomeTempoFragment extends Fragment implements ItemClickListener {

    private FragmentHomeTempoBinding binding;
    private SharedPreferences sharedPreferences;
    private TempoViewModel tempoViewModel;
    private NewsAdapter newsAdapter;
    private List<PostModel> postModelList;
    private PostViewModel postViewModel;
    private DataModel dataModel;
    private String newsType = "nasional";
    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetShare, bottomSheetWebView, bottomSheetMediaBerita;
    private String thumbnail, postTitle, postDesc, postDate, postUrl, userId, created_at, username, redactionName;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeTempoBinding.inflate(inflater, container, false);

        init();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listenerTabLayout();
        listener();
        listenerMediaBeritaPicker();
        getData("nasional");
        greetings();
        setUpBottomSheetShare();
        setUpBottomSheetWebView();
        setUpBottomSheetMediaBerita();

        binding.tvUsername.setText(sharedPreferences.getString(Constans.USERNAME, "-"));

    }

    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        tempoViewModel = new ViewModelProvider(this).get(TempoViewModel.class);
        userId = sharedPreferences.getString(Constans.USER_ID, null);
        username = sharedPreferences.getString(Constans.USERNAME, null);
        redactionName = "Tempo.co";
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

    }

    private void getData(String newsType) {
        showShimmer();
        binding.rvNews.setAdapter(null);
         if (newsType.equals("nasional")){
            tempoViewModel.getDataNasional().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");


                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("bisnis")){
            tempoViewModel.getDataBisnis().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");



                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("metro")){
            tempoViewModel.getDataMetro().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");


                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        } else if (newsType.equals("dunia")){
            tempoViewModel.getDataDunia().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");


                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("bola")){
            tempoViewModel.getDataBola().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");


                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("cantik")){
           tempoViewModel.getDataCantik().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");


                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("otomotif")){
            tempoViewModel.getDataOtomotif().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");

                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("tekno")){
            tempoViewModel.getDataTekno().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");

                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("seleb")){
            tempoViewModel.getDataSeleb().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");

                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("gaya")){
            tempoViewModel.getDataGaya().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");

                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("travel")){
            tempoViewModel.getDataTravel().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");

                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("difabel")){
            tempoViewModel.getDataDifabel().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");

                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("creativelab")){
            tempoViewModel.getDataCreativeLab().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");

                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("inforial")){
            tempoViewModel.getDataInforial().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");

                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("event")){
            tempoViewModel.getDataEvent().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    if (responseModel.getSuccess() == true && responseModel.getDataModel() != null) {
                        dataModel = responseModel.getDataModel();
                        postModelList = dataModel.getPostModelList();
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        newsAdapter = new NewsAdapter(getContext(), postModelList);
                        binding.rvNews.setAdapter(newsAdapter);
                        binding.rvNews.setLayoutManager(linearLayoutManager);
                        binding.rvNews.setHasFixedSize(true);
                        hideShimmer(false, "");

                        // set on click item
                        newsAdapter.setItemClickListener(HomeTempoFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
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




    private void listenerTabLayout() {

        binding.menuNasional.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "nasional";

            // get data
            getData("nasional");


            binding.lnNasional.setVisibility(View.VISIBLE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });


        binding.menuBisnis.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "bisnis";

            // get data
            getData("bisnis");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.VISIBLE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });


        binding.menuMetro.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "metro";

            // get data
            getData("metro");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.VISIBLE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });


        binding.menuDunia.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "dunia";

            // get data
            getData("dunia");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.VISIBLE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });


        binding.menuBola.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "bola";

            // get data
            getData("bola");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.VISIBLE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });


        binding.menuCantik.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "cantik";

            // get data
            getData("cantik");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.VISIBLE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });



        binding.menuOtomotif.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "otomotif";

            // get data
            getData("otomotif");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.VISIBLE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });



        binding.menuTekno.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "tekno";

            // get data
            getData("tekno");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.VISIBLE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });



        binding.menuSeleb.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "seleb";

            // get data
            getData("seleb");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.VISIBLE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));


        });


        binding.menuGaya.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "gaya";

            // get data
            getData("gaya");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.VISIBLE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));


        });

        binding.menuTravel.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "travel";

            // get data
            getData("travel");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.VISIBLE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });



        binding.menuDifabel.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "difabel";

            // get data
            getData("difabel");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.VISIBLE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });



        binding.menuCreativelab.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "creativelab";

            // get data
            getData("creativelab");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.VISIBLE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });



        binding.menuInforial.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "inforial";

            // get data
            getData("inforial");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.VISIBLE);
            binding.lnEvent.setVisibility(View.GONE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar_off));

        });



        binding.menuEvent.setOnClickListener(view -> {

            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMetro.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCantik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSeleb.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGaya.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTravel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDifabel.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbCreativelab.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInforial.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEvent.setTextColor(getContext().getColor(R.color.black));

            // set type
            newsType = "event";

            // get data
            getData("event");


            binding.lnNasional.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnMetro.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnCantik.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnSeleb.setVisibility(View.GONE);
            binding.lnGaya.setVisibility(View.GONE);
            binding.lnTravel.setVisibility(View.GONE);
            binding.lnDifabel.setVisibility(View.GONE);
            binding.lnCreativelab.setVisibility(View.GONE);
            binding.lnInforial.setVisibility(View.GONE);
            binding.lnEvent.setVisibility(View.VISIBLE);


            binding.ivLocal.setImageDrawable(getContext().getDrawable(R.drawable.ic_local_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivMetro.setImageDrawable(getContext().getDrawable(R.drawable.ic_city_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivCantik.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivSeleb.setImageDrawable(getContext().getDrawable(R.drawable.ic_celebrity_off));
            binding.ivGaya.setImageDrawable(getContext().getDrawable(R.drawable.ic_bag_off));
            binding.ivTravel.setImageDrawable(getContext().getDrawable(R.drawable.ic_plane_off));
            binding.ivDifabel.setImageDrawable(getContext().getDrawable(R.drawable.ic_difabel_off));
            binding.ivCreativelab.setImageDrawable(getContext().getDrawable(R.drawable.ic_paint_off));
            binding.ivInforial.setImageDrawable(getContext().getDrawable(R.drawable.ic_education_off));
            binding.ivEvent.setImageDrawable(getContext().getDrawable(R.drawable.ic_calendar));

        });





    }


    private void listener() {
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(newsType);
            }
        });

        binding.vOverlay.setOnClickListener(view -> {
            hideBottomSheetShare();
            hideBottomSheetWebView();
            hideBottomSheetMediaBerita();
        });

        binding.btnShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, postUrl);
            sendIntent.setType("text/plain");

            // Hapus preferensi pilihan pengguna (default)
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            // Membuat chooser dialog
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });



        binding.btnSimpan.setOnClickListener(view -> {
            savePost();
        });

        binding.btnMenuMedia.setOnClickListener(view -> {
            showBottomSheetMediaBerita();
        });

        binding.tvHeader.setOnClickListener(view -> {
            setWebView(Constans.URL_TEMPO);
            showBottomSheetWebView();
        });


    }

    private void listenerMediaBeritaPicker() {
        binding.mnuTempo.setOnClickListener(view -> {
            getData(newsType);
            hideBottomSheetMediaBerita();
        });

        binding.mnuKumparan.setOnClickListener(view -> {
            moveFragment(new HomeKumparanFragment());
            hideBottomSheetMediaBerita();
        });

        binding.mnuTribun.setOnClickListener(view -> {
            moveFragment(new HomeTribunNewsFragment());
        });

        binding.mnuCnbc.setOnClickListener(view -> {
            moveFragment(new HomeCnbcFragment());
        });

        binding.mnuOkezone.setOnClickListener(view -> {
            moveFragment(new HomeOkezoneFragment());
        });

        binding.mnuCnn.setOnClickListener(view -> {
            moveFragment(new HomeCnnFragment());
        });

        binding.mnuSindoNews.setOnClickListener(view -> {
            moveFragment(new HomeSindoNewsFragment());
        });

        binding.mnuAntara.setOnClickListener(view -> {
            moveFragment(new HomeAntaraFragment());
        });

        binding.mnuSuara.setOnClickListener(view -> {
            moveFragment(new HomeSuaraFragment());
        });

        binding.mnuRepublika.setOnClickListener(view -> {
            moveFragment(new HomeRepublikaFragment());
        });

        binding.mnuJpnn.setOnClickListener(view -> {
            moveFragment(new HomeJpnnFragment());
        });
    }

    private void greetings() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 12 && hour < 15) {
            binding.tvGreeting.setText("Selamat Siang,");
        } else if (hour >= 15 && hour < 18) {
            binding.tvGreeting.setText("Selamat Sore,");
        } else if (hour >= 18 && hour < 24) {
            binding.tvGreeting.setText("Selamat Malam,");
        } else {
            binding.tvGreeting.setText("Selamat Pagi,");
        }
    }

    private void hideShimmer(Boolean isEmpty, String message) {
        binding.swipeRefresh.setRefreshing(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.shimmerLayout.setVisibility(View.GONE);
                binding.shimmerLayout.stopShimmer();
                binding.rvNews.setVisibility(View.VISIBLE);
                if (isEmpty == true) {
                    binding.lrEmpty.setVisibility(View.VISIBLE);
                    binding.tvMessage.setText(message);
                }else {
                    binding.lrEmpty.setVisibility(View.GONE);
                }
            }
        }, 2000);


    }

    private void showShimmer() {
        binding.rvNews.setVisibility(View.GONE);
        binding.shimmerLayout.setVisibility(View.VISIBLE);
        binding.shimmerLayout.startShimmer();
        binding.lrEmpty.setVisibility(View.GONE);
    }

    private void setUpBottomSheetShare () {
        bottomSheetShare = BottomSheetBehavior.from(binding.rlBottomSheetShare);
        bottomSheetShare.setHideable(true);
        bottomSheetShare.setPeekHeight(0);
        bottomSheetShare.setState(BottomSheetBehavior.STATE_COLLAPSED);

        bottomSheetShare.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    hideBottomSheetShare();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }


    private void setUpBottomSheetMediaBerita () {
        bottomSheetMediaBerita = BottomSheetBehavior.from(binding.rlBottomSheetMediaPicker);
        bottomSheetMediaBerita.setHideable(true);
        bottomSheetMediaBerita.setPeekHeight(0);
        bottomSheetMediaBerita.setState(BottomSheetBehavior.STATE_COLLAPSED);

        bottomSheetMediaBerita.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    hideBottomSheetShare();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }


    private void setUpBottomSheetWebView () {
        bottomSheetWebView = BottomSheetBehavior.from(binding.RlbottomSheetWebview);
        bottomSheetWebView.setHideable(true);
        bottomSheetWebView.setPeekHeight(0);
        bottomSheetWebView.setState(BottomSheetBehavior.STATE_HIDDEN);


        bottomSheetWebView.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    hideBottomSheetWebView();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {



            }
        });
    }

    private void showBottomSheetShare() {
        bottomSheetShare.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetShare.setPeekHeight(600);
        binding.vOverlay.setVisibility(View.VISIBLE);


    }

    private void hideBottomSheetShare() {
        bottomSheetShare.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.vOverlay.setVisibility(View.GONE);
        bottomSheetShare.setPeekHeight(0);

    }

    private void hideBottomSheetMediaBerita() {
        bottomSheetMediaBerita.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.vOverlay.setVisibility(View.GONE);
        bottomSheetMediaBerita.setPeekHeight(0);

    }

    private void showBottomSheetMediaBerita() {
        bottomSheetMediaBerita.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetMediaBerita.setPeekHeight(600);
        binding.vOverlay.setVisibility(View.VISIBLE);


    }



    private void showBottomSheetWebView() {
        bottomSheetWebView.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetWebView.setPeekHeight(2000);
        bottomSheetWebView.setDraggable(false);
        binding.vOverlay.setVisibility(View.VISIBLE);

    }

    private void hideBottomSheetWebView() {
        bottomSheetWebView.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.vOverlay.setVisibility(View.GONE);

        // clean webview
        binding.webview.loadUrl("about:blank");
        binding.webview.clearCache(true);
        binding.webview.clearHistory();
        binding.webview.reload();
        binding.webview.clearFormData();
        binding.webview.clearSslPreferences();
        binding.webview.clearMatches();
        binding.webview.clearDisappearingChildren();
        binding.webview.clearAnimation();
        binding.webview.clearFocus();
        binding.webview.destroyDrawingCache();
        binding.webview.removeAllViews();


    }

    private void setWebView(String url) {
        // start shimmer
        shimmerWebView();

        // Aktifkan JavaScript
        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.webview.loadUrl(url);

    }

    private void shimmerWebView() {

        binding.webViewShimmer.setVisibility(View.VISIBLE);
        binding.webViewShimmer.startShimmer();
        binding.webview.setVisibility(View.GONE
        );
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.webViewShimmer.setVisibility(View.GONE);
                binding.webViewShimmer.stopShimmer();
                binding.webview.setVisibility(View.VISIBLE);
            }
        }, 3500);
    }

    private void savePost() {
        if (userId == null) {
            showToast(Constans.TOAST_ERROR, Constans.ERR_MESSAGE);
        }else {

            binding.btnSimpan.setBackground(getContext().getDrawable(R.drawable.ic_save_fill));

            created_at = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(new Date());
            String postId = userId + "-" + timeStamp;
            SaveModel saveModel = new SaveModel(postUrl, postTitle, postDesc, postDate, thumbnail, userId, username, created_at, redactionName, postId);


            postViewModel.insertSavePost(saveModel).observe(getViewLifecycleOwner(), new Observer<FirebaseResponseModel>() {
                @Override
                public void onChanged(FirebaseResponseModel firebaseResponseModel) {
                    if (firebaseResponseModel.getSuccess() == true) {
                        showToast(Constans.TOAST_NORMAL, firebaseResponseModel.getMessage());
                        hideBottomSheetShare();
                    }else {
                        showToast(Constans.TOAST_ERROR,Constans.ERR_MESSAGE);
                    }
                }
            });

        }



    }







    @Override
    public void onItemClickListener(String share, Object model) {
        PostModel postModel = (PostModel) model;

        // set value
        postTitle = postModel.getTitle();
        postDesc  = postModel.getDescription();
        postDate = postModel.getPubDate();
        thumbnail = postModel.getThumbnail();
        postUrl = postModel.getLink();


        // set value to bottom sheet
        binding.tvPostTitle.setText(postTitle);


        if (postDate != null) {
            String date = postDate.substring(0, 10);
            String timeStamp = postDate.substring(11, 19);
            binding.tvPostDate.setText(date + " | " + timeStamp);
        }else {
            binding.tvPostDate.setText("-");
        }




        Glide.with(getContext()).load(thumbnail)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .centerCrop()
                .dontAnimate()
                .override(300, 300)
                .into(binding.ivPost);


        // load webview
        setWebView(postUrl);

        // logic listener

        if (share.equals("share")) { // JIKA BUTTON SHARE DI KLIK DI ADAPTER
            showBottomSheetShare();
        }else {
            showBottomSheetWebView();
        }



    }
    private void moveFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, fragment)
                .addToBackStack(null).commit();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}