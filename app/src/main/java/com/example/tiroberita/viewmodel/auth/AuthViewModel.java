package com.example.tiroberita.viewmodel.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.auth.AuthRepository;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.util.constans.Constans;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;

    @Inject
    public AuthViewModel (AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    public LiveData<FirebaseResponseModel> updateUsername(String username, String userId) {
        MutableLiveData<FirebaseResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        if (username != null && username != null) {
            LiveData<FirebaseResponseModel> responseModelLiveData = authRepository.updateUsername(username, userId);
            responseModelLiveData.observeForever(new Observer<FirebaseResponseModel>() {
                @Override
                public void onChanged(FirebaseResponseModel firebaseResponseModel) {
                    responseModelMutableLiveData.setValue(firebaseResponseModel);
                    responseModelLiveData.removeObserver(this);
                }
            });
        }else {
            responseModelMutableLiveData.setValue(new FirebaseResponseModel(false, Constans.ERR_MESSAGE, null));
        }

        return responseModelMutableLiveData;
    }


    public LiveData<FirebaseResponseModel> updateMediaFavorit(String userId, String mediaName) {
        MutableLiveData<FirebaseResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        if (userId != null && mediaName != null) {
            LiveData<FirebaseResponseModel> firebaseResponseModelLiveData = authRepository.updateMediaFavorite(userId, mediaName);
            firebaseResponseModelLiveData.observeForever(new Observer<FirebaseResponseModel>() {
                @Override
                public void onChanged(FirebaseResponseModel firebaseResponseModel) {
                    responseModelMutableLiveData.setValue(firebaseResponseModel);
                    firebaseResponseModelLiveData.removeObserver(this);
                }
            });
        }else {
            responseModelMutableLiveData.setValue(new FirebaseResponseModel(false, Constans.ERR_MESSAGE, null));
        }

        return responseModelMutableLiveData;
    }


}

