package com.example.tiroberita.viewmodel.app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.app.AppRepository;
import com.example.tiroberita.model.AppModel;
import com.example.tiroberita.model.FirebaseResponseModel;
import com.example.tiroberita.model.NotificationModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AppViewModel extends ViewModel {
    private AppRepository appRepository;

    @Inject
    public  AppViewModel(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public LiveData<FirebaseResponseModel<AppModel>> checkUpdate() {
       return appRepository.updateCheck();

    }

    public LiveData<FirebaseResponseModel<NotificationModel>> checkNotification() {
        return appRepository.notficationCheck();

    }
}
