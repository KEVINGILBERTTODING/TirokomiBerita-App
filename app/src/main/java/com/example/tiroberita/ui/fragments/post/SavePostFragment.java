package com.example.tiroberita.ui.fragments.post;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiroberita.databinding.FragmentSavePostBinding;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.model.SavePostModel;
import com.example.tiroberita.ui.adapters.SavePostAdapter;
import com.example.tiroberita.util.Constans;
import com.example.tiroberita.viewmodel.post.PostViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class SavePostFragment extends Fragment {


    private FragmentSavePostBinding binding;
    private SavePostAdapter savePostAdapter;
    private SharedPreferences sharedPreferences;
    private String userId;
    private PostViewModel savePostViewModel;
    private List<SavePostModel> savePostModelList;
    private LinearLayoutManager linearLayoutManager;


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
    }


    private void init() {
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.USER_ID, null);
        savePostViewModel = new ViewModelProvider(this).get(PostViewModel.class);

    }
    private void getData() {
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
                        binding.rvPost.setHasFixedSize(true);
                    }else {
                        showToast(Constans.TOAST_NORMAL, "Tidak ada data");
                    }
                }else {
                    showToast(Constans.TOAST_ERROR, listFirebaseResponseModel.getMessage());
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

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}