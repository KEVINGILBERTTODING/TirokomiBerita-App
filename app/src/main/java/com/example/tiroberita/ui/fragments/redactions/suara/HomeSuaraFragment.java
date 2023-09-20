package com.example.tiroberita.ui.fragments.redactions.suara;

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
import com.example.tiroberita.databinding.FragmentHomeCnnBinding;
import com.example.tiroberita.databinding.FragmentHomeSuaraBinding;
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
import com.example.tiroberita.ui.fragments.redactions.tempo.HomeTempoFragment;
import com.example.tiroberita.ui.fragments.redactions.tribun.HomeTribunNewsFragment;
import com.example.tiroberita.util.Constans;
import com.example.tiroberita.viewmodel.cnn.CnnViewModel;
import com.example.tiroberita.viewmodel.post.PostViewModel;
import com.example.tiroberita.viewmodel.suara.SuaraViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class HomeSuaraFragment extends Fragment implements ItemClickListener {

    private FragmentHomeSuaraBinding binding;
    private SharedPreferences sharedPreferences;
    private SuaraViewModel suaraViewModel;
    private NewsAdapter newsAdapter;
    private List<PostModel> postModelList;
    private PostViewModel postViewModel;
    private DataModel dataModel;
    private String newsType = "terbaru";
    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetShare, bottomSheetWebView, bottomSheetMediaBerita;
    private String thumbnail, postTitle, postDesc, postDate, postUrl, userId, created_at, username, redactionName;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeSuaraBinding.inflate(inflater, container, false);

        init();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listenerTabLayout();
        listener();
        listenerMediaBeritaPicker();
        getData("terbaru");
        greetings();
        setUpBottomSheetShare();
        setUpBottomSheetWebView();
        setUpBottomSheetMediaBerita();

        binding.tvUsername.setText(sharedPreferences.getString(Constans.USERNAME, "-"));

    }

    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        suaraViewModel = new ViewModelProvider(this).get(SuaraViewModel.class);
        userId = sharedPreferences.getString(Constans.USER_ID, null);
        username = sharedPreferences.getString(Constans.USERNAME, null);
        redactionName = "Suara.com";
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

    }

    private void getData(String newsType) {
        showShimmer();
        binding.rvNews.setAdapter(null);
        if (newsType.equals("terbaru")){
            suaraViewModel.getDataTerbaru().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeSuaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("bisnis")){
            suaraViewModel.getDataBisnis().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeSuaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("bola")){
            suaraViewModel.getDataBola().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeSuaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("lifestyle")){
            suaraViewModel.getDataLifeStyle().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeSuaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        } else if (newsType.equals("entertainment")){
            suaraViewModel.getDataEntertainment().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeSuaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("otomotif")){
            suaraViewModel.getDataOtomotif().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeSuaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("tekno")){
            suaraViewModel.getDataTekno().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeSuaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("health")){
            suaraViewModel.getDataHealth().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeSuaraFragment.this);

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
        binding.menuTerbaru.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntertainment.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHealth.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "terbaru";

            // get data
            getData("terbaru");



            binding.lnTerbaru.setVisibility(View.VISIBLE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnEntertainment.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnHealth.setVisibility(View.GONE);



            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_star));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivEntertainment.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivHealth.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));

        });


        binding.menuBisnis.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntertainment.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHealth.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "bisnis";

            // get data
            getData("bisnis");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.VISIBLE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnEntertainment.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnHealth.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivEntertainment.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivHealth.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));

        });



        binding.menuBola.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntertainment.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHealth.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "bola";

            // get data
            getData("bola");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.VISIBLE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnEntertainment.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnHealth.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivEntertainment.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivHealth.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));

        });


        binding.menuLifestyle.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbEntertainment.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHealth.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "lifestyle";

            // get data
            getData("lifestyle");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.VISIBLE);
            binding.lnEntertainment.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnHealth.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style));
            binding.ivEntertainment.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivHealth.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));

        });


        binding.menuEntertainment.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntertainment.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHealth.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "entertainment";

            // get data
            getData("entertainment");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnEntertainment.setVisibility(View.VISIBLE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnHealth.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivEntertainment.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivHealth.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));

        });


        binding.menuOtomotif.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntertainment.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHealth.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "otomotif";

            // get data
            getData("otomotif");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnEntertainment.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.VISIBLE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnHealth.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivEntertainment.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivHealth.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));

        });


        binding.menuTekno.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntertainment.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbHealth.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "tekno";

            // get data
            getData("tekno");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnEntertainment.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.VISIBLE);
            binding.lnHealth.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivEntertainment.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech));
            binding.ivHealth.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart_off));

        });


        binding.menuHealth.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBisnis.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntertainment.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTekno.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHealth.setTextColor(getContext().getColor(R.color.black));

            // set type
            newsType = "health";

            // get data
            getData("health");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnBisnis.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnEntertainment.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnTekno.setVisibility(View.GONE);
            binding.lnHealth.setVisibility(View.VISIBLE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivBisnis.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivEntertainment.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivTekno.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivHealth.setImageDrawable(getContext().getDrawable(R.drawable.ic_heart));

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
            setWebView(Constans.URL_SUARA);
            showBottomSheetWebView();
        });


    }

    private void listenerMediaBeritaPicker() {
        binding.mnuSuara.setOnClickListener(view -> {
            getData("terbaru");
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

        binding.mnuTempo.setOnClickListener(view -> {
            moveFragment(new HomeTempoFragment());
        });

        binding.mnuAntara.setOnClickListener(view -> {
            moveFragment(new HomeAntaraFragment());
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


        String date = postDate.substring(0, 10);
        String timeStamp = postDate.substring(11, 19);
        binding.tvPostDate.setText(date + " | " + timeStamp);


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