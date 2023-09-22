package com.example.tiroberita.ui.fragments.redactions.cnbc;

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
import com.example.tiroberita.databinding.FragmentHomeCnbcBinding;
import com.example.tiroberita.model.DataModel;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.model.PostModel;
import com.example.tiroberita.model.ResponseModel;
import com.example.tiroberita.model.SaveModel;
import com.example.tiroberita.ui.ItemClickListener;
import com.example.tiroberita.ui.adapters.NewsAdapter;
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
import com.example.tiroberita.viewmodel.cnbc.CnbcViewModel;
import com.example.tiroberita.viewmodel.post.PostViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class HomeCnbcFragment extends Fragment implements ItemClickListener {

    private FragmentHomeCnbcBinding binding;
    private SharedPreferences sharedPreferences;
    private CnbcViewModel cnbcViewModel;
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

        binding = FragmentHomeCnbcBinding.inflate(inflater, container, false);

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
        cnbcViewModel = new ViewModelProvider(this).get(CnbcViewModel.class);
        userId = sharedPreferences.getString(Constans.USER_ID, null);
        username = sharedPreferences.getString(Constans.USERNAME, null);
        redactionName = "CNBC Indonesia";
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

    }

    private void getData(String newsType) {
        showShimmer();
        binding.rvNews.setAdapter(null);
        if (newsType.equals("terbaru")){
            cnbcViewModel.getDataTerbaru().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeCnbcFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("news")){
            cnbcViewModel.getDataNews().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeCnbcFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("market")){
            cnbcViewModel.getDataMarket().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeCnbcFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("entrepreneur")){
            cnbcViewModel.getDataEntrepreneur().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeCnbcFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        } else if (newsType.equals("syariah")){
            cnbcViewModel.getDataSyariah().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeCnbcFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("tech")){
            cnbcViewModel.getDataTech().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeCnbcFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("opini")){
            cnbcViewModel.getDataOpini().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeCnbcFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("lifestyle")){
            cnbcViewModel.getDataLifeStyle().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeCnbcFragment.this);

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
            binding.tvTbNews.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMarket.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntrepreneur.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSyariah.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTech.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifeStyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOpini.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "terbaru";

            // get data
            getData("terbaru");



            binding.lnTerbaru.setVisibility(View.VISIBLE);
            binding.lnNews.setVisibility(View.GONE);
            binding.lnMarket.setVisibility(View.GONE);
            binding.lnEntrepreneur.setVisibility(View.GONE);
            binding.lnSyariah.setVisibility(View.GONE);
            binding.lnTech.setVisibility(View.GONE);
            binding.lnLifeStyle.setVisibility(View.GONE);
            binding.lnOpini.setVisibility(View.GONE);


            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_star));
            binding.ivNews.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivMarket.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivEntrepreneur.setImageDrawable(getContext().getDrawable(R.drawable.ic_entrepreneur_off));
            binding.ivSyariah.setImageDrawable(getContext().getDrawable(R.drawable.ic_syariah_off));
            binding.ivTech.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivLifeStyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivOpini.setImageDrawable(getContext().getDrawable(R.drawable.ic_opinion_off));

        });

        binding.menuNews.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNews.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbMarket.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntrepreneur.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSyariah.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTech.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifeStyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOpini.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "news";

            // get data
            getData("news");



            // Set Underline
            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNews.setVisibility(View.VISIBLE);
            binding.lnMarket.setVisibility(View.GONE);
            binding.lnEntrepreneur.setVisibility(View.GONE);
            binding.lnSyariah.setVisibility(View.GONE);
            binding.lnTech.setVisibility(View.GONE);
            binding.lnLifeStyle.setVisibility(View.GONE);
            binding.lnOpini.setVisibility(View.GONE);

            // set disable icon

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivNews.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional));
            binding.ivMarket.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivEntrepreneur.setImageDrawable(getContext().getDrawable(R.drawable.ic_entrepreneur_off));
            binding.ivSyariah.setImageDrawable(getContext().getDrawable(R.drawable.ic_syariah_off));
            binding.ivTech.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivLifeStyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivOpini.setImageDrawable(getContext().getDrawable(R.drawable.ic_opinion_off));
        });

        binding.menuMarket.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNews.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMarket.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbEntrepreneur.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSyariah.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTech.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifeStyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOpini.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "market";

            // get data
            getData("market");



            // Set Underline
            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNews.setVisibility(View.GONE);
            binding.lnMarket.setVisibility(View.VISIBLE);
            binding.lnEntrepreneur.setVisibility(View.GONE);
            binding.lnSyariah.setVisibility(View.GONE);
            binding.lnTech.setVisibility(View.GONE);
            binding.lnLifeStyle.setVisibility(View.GONE);
            binding.lnOpini.setVisibility(View.GONE);

            // set disable icon

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivNews.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivMarket.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi));
            binding.ivEntrepreneur.setImageDrawable(getContext().getDrawable(R.drawable.ic_entrepreneur_off));
            binding.ivSyariah.setImageDrawable(getContext().getDrawable(R.drawable.ic_syariah_off));
            binding.ivTech.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivLifeStyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivOpini.setImageDrawable(getContext().getDrawable(R.drawable.ic_opinion_off));
        });


        binding.menuEntrepreneur.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNews.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMarket.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntrepreneur.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbSyariah.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTech.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifeStyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOpini.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "entrepreneur";

            // get data
            getData("entrepreneur");



            // Set Underline
            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNews.setVisibility(View.GONE);
            binding.lnMarket.setVisibility(View.GONE);
            binding.lnEntrepreneur.setVisibility(View.VISIBLE);
            binding.lnSyariah.setVisibility(View.GONE);
            binding.lnTech.setVisibility(View.GONE);
            binding.lnLifeStyle.setVisibility(View.GONE);
            binding.lnOpini.setVisibility(View.GONE);

            // set disable icon

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivNews.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivMarket.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivEntrepreneur.setImageDrawable(getContext().getDrawable(R.drawable.ic_entrepreneur));
            binding.ivSyariah.setImageDrawable(getContext().getDrawable(R.drawable.ic_syariah_off));
            binding.ivTech.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivLifeStyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivOpini.setImageDrawable(getContext().getDrawable(R.drawable.ic_opinion_off));
        });

        binding.menuSyariah.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNews.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMarket.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntrepreneur.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSyariah.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTech.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifeStyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOpini.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "syariah";

            // get data
            getData("syariah");



            // Set Underline
            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNews.setVisibility(View.GONE);
            binding.lnMarket.setVisibility(View.GONE);
            binding.lnEntrepreneur.setVisibility(View.GONE);
            binding.lnSyariah.setVisibility(View.VISIBLE);
            binding.lnTech.setVisibility(View.GONE);
            binding.lnLifeStyle.setVisibility(View.GONE);
            binding.lnOpini.setVisibility(View.GONE);

            // set disable icon

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivNews.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivMarket.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivEntrepreneur.setImageDrawable(getContext().getDrawable(R.drawable.ic_entrepreneur_off));
            binding.ivSyariah.setImageDrawable(getContext().getDrawable(R.drawable.ic_syariah));
            binding.ivTech.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivLifeStyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivOpini.setImageDrawable(getContext().getDrawable(R.drawable.ic_opinion_off));
        });


        binding.menuSyariah.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNews.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMarket.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntrepreneur.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSyariah.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTech.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifeStyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOpini.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "syariah";

            // get data
            getData("syariah");



            // Set Underline
            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNews.setVisibility(View.GONE);
            binding.lnMarket.setVisibility(View.GONE);
            binding.lnEntrepreneur.setVisibility(View.GONE);
            binding.lnSyariah.setVisibility(View.VISIBLE);
            binding.lnTech.setVisibility(View.GONE);
            binding.lnLifeStyle.setVisibility(View.GONE);
            binding.lnOpini.setVisibility(View.GONE);

            // set disable icon

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivNews.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivMarket.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivEntrepreneur.setImageDrawable(getContext().getDrawable(R.drawable.ic_entrepreneur_off));
            binding.ivSyariah.setImageDrawable(getContext().getDrawable(R.drawable.ic_syariah));
            binding.ivTech.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivLifeStyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivOpini.setImageDrawable(getContext().getDrawable(R.drawable.ic_opinion_off));
        });

        binding.menuTech.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNews.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMarket.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntrepreneur.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSyariah.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTech.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbLifeStyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOpini.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "tech";

            // get data
            getData("tech");



            // Set Underline
            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNews.setVisibility(View.GONE);
            binding.lnMarket.setVisibility(View.GONE);
            binding.lnEntrepreneur.setVisibility(View.GONE);
            binding.lnSyariah.setVisibility(View.GONE);
            binding.lnTech.setVisibility(View.VISIBLE);
            binding.lnLifeStyle.setVisibility(View.GONE);
            binding.lnOpini.setVisibility(View.GONE);

            // set disable icon

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivNews.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivMarket.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivEntrepreneur.setImageDrawable(getContext().getDrawable(R.drawable.ic_entrepreneur_off));
            binding.ivSyariah.setImageDrawable(getContext().getDrawable(R.drawable.ic_syariah_off));
            binding.ivTech.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech));
            binding.ivLifeStyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivOpini.setImageDrawable(getContext().getDrawable(R.drawable.ic_opinion_off));
        });

        binding.menuLifeStyle.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNews.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMarket.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntrepreneur.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSyariah.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTech.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifeStyle.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbOpini.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "lifestyle";

            // get data
            getData("lifestyle");



            // Set Underline
            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNews.setVisibility(View.GONE);
            binding.lnMarket.setVisibility(View.GONE);
            binding.lnEntrepreneur.setVisibility(View.GONE);
            binding.lnSyariah.setVisibility(View.GONE);
            binding.lnTech.setVisibility(View.GONE);
            binding.lnLifeStyle.setVisibility(View.VISIBLE);
            binding.lnOpini.setVisibility(View.GONE);

            // set disable icon

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivNews.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivMarket.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivEntrepreneur.setImageDrawable(getContext().getDrawable(R.drawable.ic_entrepreneur_off));
            binding.ivSyariah.setImageDrawable(getContext().getDrawable(R.drawable.ic_syariah_off));
            binding.ivTech.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivLifeStyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style));
            binding.ivOpini.setImageDrawable(getContext().getDrawable(R.drawable.ic_opinion_off));
        });

        binding.menuOpini.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbNews.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbMarket.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEntrepreneur.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbSyariah.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTech.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifeStyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOpini.setTextColor(getContext().getColor(R.color.black));

            // set type
            newsType = "opini";

            // get data
            getData("opini");



            // Set Underline
            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnNews.setVisibility(View.GONE);
            binding.lnMarket.setVisibility(View.GONE);
            binding.lnEntrepreneur.setVisibility(View.GONE);
            binding.lnSyariah.setVisibility(View.GONE);
            binding.lnTech.setVisibility(View.GONE);
            binding.lnLifeStyle.setVisibility(View.GONE);
            binding.lnOpini.setVisibility(View.VISIBLE);

            // set disable icon

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivNews.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));
            binding.ivMarket.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivEntrepreneur.setImageDrawable(getContext().getDrawable(R.drawable.ic_entrepreneur_off));
            binding.ivSyariah.setImageDrawable(getContext().getDrawable(R.drawable.ic_syariah_off));
            binding.ivTech.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivLifeStyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivOpini.setImageDrawable(getContext().getDrawable(R.drawable.ic_opinion));
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
            setWebView(Constans.URL_CNBC_INDONESIA);
            showBottomSheetWebView();
        });


    }

    private void listenerMediaBeritaPicker() {
        binding.mnuCnbc.setOnClickListener(view -> {
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
            moveFragment(new HomeCnnFragment());
        });

        binding.mnuOkezone.setOnClickListener(view -> {
            moveFragment(new HomeOkezoneFragment());
        });

        binding.mnuAntara.setOnClickListener(view -> {
            moveFragment(new HomeCnnFragment());
        });

        binding.mnuSindoNews.setOnClickListener(view -> {
            moveFragment(new HomeSindoNewsFragment());
        });

        binding.mnuTempo.setOnClickListener(view -> {
            moveFragment(new HomeTempoFragment());
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
        binding.btnSimpan.setBackground(getContext().getDrawable(R.drawable.ic_save));



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


}