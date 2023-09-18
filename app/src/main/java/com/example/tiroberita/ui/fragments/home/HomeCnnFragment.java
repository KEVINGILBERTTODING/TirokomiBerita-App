package com.example.tiroberita.ui.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Calendar;
import java.util.List;

import com.example.tiroberita.R;
import com.example.tiroberita.databinding.FragmentHomeCnnBinding;
import com.example.tiroberita.model.DataModel;
import com.example.tiroberita.model.PostModel;
import com.example.tiroberita.model.ResponseModel;
import com.example.tiroberita.ui.adapters.NewsAdapter;
import com.example.tiroberita.util.Constans;
import com.example.tiroberita.viewmodel.cnn.CnnViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class HomeCnnFragment extends Fragment {

    private FragmentHomeCnnBinding binding;
    private SharedPreferences sharedPreferences;
    private CnnViewModel cnnViewModel;
    private NewsAdapter newsAdapter;
    private List<PostModel> postModelList;
    private DataModel dataModel;
    private String newsType = "terbaru";
    private LinearLayoutManager linearLayoutManager;



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
        getData("terbaru");
        greetings();

        binding.tvUsername.setText(sharedPreferences.getString(Constans.USERNAME, "-"));

    }

    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        cnnViewModel = new ViewModelProvider(this).get(CnnViewModel.class);
    }

    private void getData(String newsType) {
        showShimmer();
        binding.rvNews.setAdapter(null);
        if (newsType.equals("terbaru")){
            cnnViewModel.getDataTerbaru().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("nasional")){
            cnnViewModel.getDataNasional().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("olahraga")){
            cnnViewModel.getDataOlahraga().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("teknologi")){
            cnnViewModel.getDataTeknologi().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        } else if (newsType.equals("ekonomi")){
            cnnViewModel.getDataEkonomi().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("internasional")){
            cnnViewModel.getDataInternasional().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("hiburan")){
            cnnViewModel.getDataHiburan().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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

                    }else {
                        hideShimmer(true, responseModel.getMessage());

                    }
                }
            });
        }else if (newsType.equals("gayahidup")){
            cnnViewModel.getDataGayaHidup().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
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
            binding.tvTbNasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbInternasional.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbEkonomi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbOlahraga.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbTeknologi.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbHiburan.setTextColor(getContext().getColor(R.color.second_font));
            binding.tvTbGayaHidup.setTextColor(getContext().getColor(R.color.second_font));

            // set type
            newsType = "terbaru";

            // get data
            getData("terbaru");



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

            // set type
            newsType = "nasional";

            // get data
            getData("nasional");


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


            // set type
            newsType = "internasional";

            // get data
            getData("internasional");


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


            // set type
            newsType = "ekonomi";

            // get data
            getData("ekonomi");


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

            // set type
            newsType = "olahraga";
            // get data
            getData("olahraga");


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

            // set type
            newsType = "teknologi";

            // get data
            getData("teknologi");

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

            // set type
            newsType = "hiburan";


            // get data
            getData("hiburan");

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

            // set type
            newsType = "gayahidup";

            // get data
            getData("gayahidup");


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
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(newsType);
            }
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




    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}