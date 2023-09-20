package com.example.tiroberita.ui.fragments.post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tiroberita.databinding.FragmentSavePostBinding;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.model.SavePostModel;
import com.example.tiroberita.ui.ItemClickListener;
import com.example.tiroberita.ui.adapters.SavePostAdapter;
import com.example.tiroberita.util.Constans;
import com.example.tiroberita.viewmodel.post.PostViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class SavePostFragment extends Fragment implements ItemSavePostListener {


    private FragmentSavePostBinding binding;
    private SavePostAdapter savePostAdapter;
    private SharedPreferences sharedPreferences;
    private String userId;
    private PostViewModel savePostViewModel;
    private List<SavePostModel> savePostModelList;
    private LinearLayoutManager linearLayoutManager;
    private BottomSheetBehavior bottomSheetShare;
    private String thumbnail, postTitle, postDate,  postUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSavePostBinding.inflate(inflater, container, false);
        init();


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
        listener();
        setUpBottomSheetShare();
    }

    private void listener() {
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        binding.vOverlay.setOnClickListener(view -> {
            hideBottomSheetShare();
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

    }


    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.USER_ID, null);
        savePostViewModel = new ViewModelProvider(this).get(PostViewModel.class);

    }
    private void getData() {

        showShimmer();
        binding.rvPost.setAdapter(null);
        savePostViewModel.getSavePost(userId).observe(getViewLifecycleOwner(), new Observer<FirebaseResponseModel<List<SavePostModel>>>() {
            @Override
            public void onChanged(FirebaseResponseModel<List<SavePostModel>> listFirebaseResponseModel) {
                if (listFirebaseResponseModel.getSuccess() == true) {
                    savePostModelList = listFirebaseResponseModel.getData();
                    if (listFirebaseResponseModel.getData().size() > 0) {
                        savePostAdapter = new SavePostAdapter(getContext(), savePostModelList);
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        binding.rvPost.setAdapter(savePostAdapter);
                        binding.rvPost.setLayoutManager(linearLayoutManager);
                        savePostAdapter.setItemClickListener(SavePostFragment.this);
                        binding.rvPost.setHasFixedSize(true);
                        hideShimmer(false);



                    }else {

                        binding.tvMessage.setText("Belum ada berita yang disimpan.");
                        hideShimmer(true);
                    }
                }else {
                    showToast(Constans.TOAST_ERROR, listFirebaseResponseModel.getMessage());
                    hideShimmer(true);
                }
            }


        });

    }

    private void deleteSavePost(String postId, int position){
        savePostViewModel.deleteSavePost(postId).observe(getViewLifecycleOwner(), new Observer<FirebaseResponseModel>() {
            @Override
            public void onChanged(FirebaseResponseModel firebaseResponseModel) {
                if (firebaseResponseModel.getSuccess() == true) {
                    showToast(Constans.TOAST_NORMAL, firebaseResponseModel.getMessage());
                    savePostAdapter.removeItem(position);
                }else {
                    showToast(Constans.ERR_MESSAGE, firebaseResponseModel.getMessage());
                }
            }
        });
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

    private void showShimmer() {
        binding.rvPost.setVisibility(View.GONE);
        binding.shimmerLayout.setVisibility(View.VISIBLE);
        binding.shimmerLayout.startShimmer();
        binding.lrEmpty.setVisibility(View.GONE);


    }

    private void hideShimmer(Boolean isEmpty) {
        binding.swipeRefresh.setRefreshing(false);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.shimmerLayout.stopShimmer();
                binding.shimmerLayout.setVisibility(View.GONE);
                binding.rvPost.setVisibility(View.VISIBLE);
                binding.lrEmpty.setVisibility(View.VISIBLE);
                if (isEmpty == true) {
                    binding.lrEmpty.setVisibility(View.VISIBLE);
                }else {
                    binding.lrEmpty.setVisibility(View.GONE);
                }
            }
        }, 2000);
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

    private void directPost(String postUrl) {
        // buka browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(postUrl));
        startActivity(browserIntent);
    }



    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }



    @Override
    public void itemSavePostListener(String type, int position, Object model) {
        SavePostModel savePostModel = (SavePostModel) model;
        postTitle = savePostModel.getTitle();
        postUrl = savePostModel.getLink();
        thumbnail = savePostModel.getThumbnail();
        postDate = savePostModel.getPubDate();

        if (postDate != null) {
            String dateTime = postDate.substring(0, 10);
            String timStamp = postDate.substring(11, 19);
            binding.tvPostDate.setText(dateTime + " | " + timStamp);
        }else {
            binding.tvPostDate.setText("-");
        }

        Glide.with(getContext()).load(thumbnail).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .override(300, 300)
                .centerCrop()
                .into(binding.ivPost);


        if (type.equals("delete")) {
            deleteSavePost(savePostModel.getPost_id(), position);
        }else if (type.equals("share")){
            showBottomSheetShare();
        }else if (type.equals("content")){
            directPost(postUrl);
        }
    }
}