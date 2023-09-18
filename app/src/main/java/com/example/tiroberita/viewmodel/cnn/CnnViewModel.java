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
    public LiveData<ResponseModel> getDataNasional(){
        return cnnRepository.getDataNasional();
    }
    public LiveData<ResponseModel> getDataInternasional(){
        return cnnRepository.getDataInternasional();
    }

    public LiveData<ResponseModel> getDataEkonomi(){
        return cnnRepository.getDataEkonomi();
    }
    public LiveData<ResponseModel> getDataOlahraga(){
        return cnnRepository.getDataOlahraga();
    }
    public LiveData<ResponseModel> getDataTeknologi(){
        return cnnRepository.getDataTeknologi();
    }
    public LiveData<ResponseModel> getDataHiburan(){
        return cnnRepository.getDataHiburan();
    }
    public LiveData<ResponseModel> getDataGayaHidup(){
        return cnnRepository.getDataGayaHidup();
    }

}
