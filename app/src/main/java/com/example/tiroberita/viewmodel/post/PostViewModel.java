package com.example.tiroberita.viewmodel.post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.post.PostRepository;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.model.SaveModel;
import com.example.tiroberita.model.SavePostModel;
import com.example.tiroberita.util.constans.Constans;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PostViewModel extends ViewModel {
    private final PostRepository postRepository;


    @Inject
    public PostViewModel(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public LiveData<FirebaseResponseModel<List<SavePostModel>>> getSavePost(String userId) {
       return postRepository.getDatasavePost(userId);
    }

    public LiveData<FirebaseResponseModel> insertSavePost(SaveModel saveModel) {
        MutableLiveData<FirebaseResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        if (saveModel == null) {
            responseModelMutableLiveData.setValue(new FirebaseResponseModel(false, Constans.ERR_MESSAGE, null));
        }else {
            LiveData<FirebaseResponseModel> responseModelLiveData = postRepository.insertSavePost(saveModel);
            responseModelLiveData.observeForever(new Observer<FirebaseResponseModel>() {
                @Override
                public void onChanged(FirebaseResponseModel firebaseResponseModel) {
                    responseModelMutableLiveData.setValue(firebaseResponseModel);
                    responseModelLiveData.removeObserver(this);
                }
            });
        }
        return responseModelMutableLiveData;
    }

    public LiveData<FirebaseResponseModel> deleteSavePost(String postId){
        MutableLiveData<FirebaseResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        if (postId == null) {
            responseModelMutableLiveData.setValue(new FirebaseResponseModel(false, Constans.ERR_MESSAGE, null));
        }else {
            LiveData<FirebaseResponseModel> responseModelLiveData = postRepository.deleteSavePost(postId);
            responseModelLiveData.observeForever(new Observer<FirebaseResponseModel>() {
                @Override
                public void onChanged(FirebaseResponseModel firebaseResponseModel) {
                    responseModelMutableLiveData.setValue(firebaseResponseModel);
                    responseModelLiveData.removeObserver(this);
                }
            });
        }

        return responseModelMutableLiveData;
    }
}
