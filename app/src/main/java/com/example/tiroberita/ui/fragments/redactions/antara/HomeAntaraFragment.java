package com.example.tiroberita.ui.fragments.redactions.antara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.tiroberita.databinding.FragmentHomeAntaraBinding;
import com.example.tiroberita.model.DataModel;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.model.PostModel;
import com.example.tiroberita.model.ResponseModel;
import com.example.tiroberita.model.SaveModel;
import com.example.tiroberita.ui.ItemClickListener;
import com.example.tiroberita.ui.adapters.NewsAdapter;
import com.example.tiroberita.ui.fragments.redactions.cnbc.HomeCnbcFragment;
import com.example.tiroberita.ui.fragments.redactions.cnn.HomeCnnFragment;
import com.example.tiroberita.ui.fragments.redactions.kumparan.HomeKumparanFragment;
import com.example.tiroberita.ui.fragments.redactions.okezone.HomeOkezoneFragment;
import com.example.tiroberita.ui.fragments.redactions.tribun.HomeTribunNewsFragment;
import com.example.tiroberita.util.Constans;
import com.example.tiroberita.viewmodel.antara.AntaraViewModel;
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
public class HomeAntaraFragment extends Fragment implements ItemClickListener {

    private FragmentHomeAntaraBinding binding;
    private SharedPreferences sharedPreferences;
    private AntaraViewModel antaraViewModel;
    private NewsAdapter newsAdapter;
    private List<PostModel> postModelList;
    private PostViewModel postViewModel;
    private DataModel dataModel;
    private String newsType = "terbaru";
    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetShare, bottomSheetMediaBerita;
    private String thumbnail, postTitle, postDesc, postDate, postUrl, userId, created_at, username, redactionName;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeAntaraBinding.inflate(inflater, container, false);

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
        setUpBottomSheetMediaBerita();

        binding.tvUsername.setText(sharedPreferences.getString(Constans.USERNAME, "-"));

    }

    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        antaraViewModel = new ViewModelProvider(this).get(AntaraViewModel.class);
        userId = sharedPreferences.getString(Constans.USER_ID, null);
        username = sharedPreferences.getString(Constans.USERNAME, null);
        redactionName = "Antaranews.com";
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

    }

    private void getData(String newsType) {
        showShimmer();
        binding.rvNews.setAdapter(null);
        if (newsType.equals("terbaru")){
            antaraViewModel.getDataTerbaru().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("politik")){
            antaraViewModel.getDataPolitik().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("ekonomi")){
            antaraViewModel.getDataEkonomi().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("bola")){
            antaraViewModel.getDataBola().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("olahraga")){
            antaraViewModel.getDataOlahraga().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("teknologi")){
            antaraViewModel.getDataTekno().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("otomotif")){
            antaraViewModel.getDataOtomotif().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("hiburan")){
            antaraViewModel.getDataHiburan().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("lifestyle")){
            antaraViewModel.getDataLifestyle().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("hukum")){
            antaraViewModel.getDataHukum().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("humaniora")){
            antaraViewModel.getDataHumaniora().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });

        }else if (newsType.equals("dunia")){
            antaraViewModel.getDataDunia().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
                        newsAdapter.setItemClickListener(HomeAntaraFragment.this);

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
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "terbaru";

            // get data
            getData("terbaru");



            binding.lnTerbaru.setVisibility(View.VISIBLE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_star));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));

        });


        binding.menuPolitik.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "politik";

            // get data
            getData("politik");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.VISIBLE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));

        });


        binding.menuEkonomi.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "ekonomi";

            // get data
            getData("ekonomi");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.VISIBLE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));

        });


        binding.menuBola.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "bola";

            // get data
            getData("bola");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.VISIBLE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));

        });


        binding.menuOlahraga.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "olahraga";

            // get data
            getData("olahraga");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.VISIBLE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));

        });


        binding.menuTeknologi.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "teknologi";

            // get data
            getData("teknologi");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.VISIBLE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));

        });


        binding.menuOtomotif.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "otomotif";

            // get data
            getData("otomotif");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.VISIBLE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));

        });


        binding.menuHiburan.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "hiburan";

            // get data
            getData("hiburan");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.VISIBLE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));

        });


        binding.menuLifestyle.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "lifestyle";

            // get data
            getData("lifestyle");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.VISIBLE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));


        });


        binding.menuHukum.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "hukum";

            // get data
            getData("hukum");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.VISIBLE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));


        });


        binding.menuHumaniora.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.black));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "humaniora";

            // get data
            getData("humaniora");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.VISIBLE);
            binding.lnDunia.setVisibility(View.GONE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional_off));


        });


        binding.menuDunia.setOnClickListener(view -> {
            binding.tvTbTerbaru.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbPolitik.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbBola.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOtomotif.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbLifestyle.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHukum.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHumaniora.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbDunia.setTextColor(getContext().getColor(R.color.black));

            // set type
            newsType = "dunia";

            // get data
            getData("dunia");

            binding.lnTerbaru.setVisibility(View.GONE);
            binding.lnPolitik.setVisibility(View.GONE);
            binding.lnEkonomi.setVisibility(View.GONE);
            binding.lnBola.setVisibility(View.GONE);
            binding.lnOlahraga.setVisibility(View.GONE);
            binding.lnTeknologi.setVisibility(View.GONE);
            binding.lnOtomotif.setVisibility(View.GONE);
            binding.lnHiburan.setVisibility(View.GONE);
            binding.lnLifestyle.setVisibility(View.GONE);
            binding.lnHukum.setVisibility(View.GONE);
            binding.lnHumaniora.setVisibility(View.GONE);
            binding.lnDunia.setVisibility(View.VISIBLE);

            binding.ivTerbaru.setImageDrawable(getContext().getDrawable(R.drawable.ic_terbaru_off));
            binding.ivPoilitik.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivEkonomi.setImageDrawable(getContext().getDrawable(R.drawable.ic_ekonomi_off));
            binding.ivBola.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport_off));
            binding.ivOlahraga.setImageDrawable(getContext().getDrawable(R.drawable.ic_sport2_off));
            binding.ivTeknologi.setImageDrawable(getContext().getDrawable(R.drawable.ic_tech_off));
            binding.ivOtomotif.setImageDrawable(getContext().getDrawable(R.drawable.ic_otomotif_off));
            binding.ivHiburan.setImageDrawable(getContext().getDrawable(R.drawable.ic_entertaiment_off));
            binding.ivLifestyle.setImageDrawable(getContext().getDrawable(R.drawable.ic_life_style_off));
            binding.ivHukum.setImageDrawable(getContext().getDrawable(R.drawable.ic_law_off));
            binding.ivHumaniora.setImageDrawable(getContext().getDrawable(R.drawable.ic_humanity_off));
            binding.ivDunia.setImageDrawable(getContext().getDrawable(R.drawable.ic_internasional));


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
            directPost(Constans.URL_ANTARA);
        });


    }

    private void listenerMediaBeritaPicker() {
        binding.mnuAntara.setOnClickListener(view -> {
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



    private void directPost(String postUrl) {
        // buka browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(postUrl));
        startActivity(browserIntent);
    }

    private void savePost() {
        if (userId == null) {
            showToast(Constans.TOAST_ERROR, Constans.ERR_MESSAGE);
        }else {

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



        // logic listener

        if (share.equals("share")) { // JIKA BUTTON SHARE DI KLIK DI ADAPTER
            showBottomSheetShare();
        }else {
            directPost(postUrl);
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