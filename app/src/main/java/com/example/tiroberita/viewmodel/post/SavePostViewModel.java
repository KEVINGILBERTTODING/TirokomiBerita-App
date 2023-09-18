package com.example.tiroberita.viewmodel.post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.post.SavePostRepository;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.model.SavePostModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SavePostViewModel extends ViewModel {
    private final SavePostRepository savePostRepository;


    @Inject
    public SavePostViewModel(SavePostRepository savePostRepository) {
        this.savePostRepository = savePostRepository;
    }
    public LiveData<FirebaseResponseModel<List<SavePostModel>>> getSavePost(String userId) {
       return savePostRepository.savePost(userId);
    }
}
