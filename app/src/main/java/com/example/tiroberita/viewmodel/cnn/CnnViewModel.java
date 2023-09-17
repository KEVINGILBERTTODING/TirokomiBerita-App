package com.example.tiroberita.viewmodel.cnn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.cnn.CnnRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CnnViewModel extends ViewModel {
    private CnnRepository cnnRepository;

    @Inject
    public CnnViewModel(CnnRepository cnnRepository) {
        this.cnnRepository = cnnRepository;
    }

    public LiveData<ResponseModel> getDataTerbaru(){
        return cnnRepository.getDataTerbaru();
    }
}
