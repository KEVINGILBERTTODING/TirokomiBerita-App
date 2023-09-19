package com.example.tiroberita.viewmodel.cnbc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.cnbc.CnbcRepository;
import com.example.tiroberita.data.repository.kumparan.KumparanRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CnbcViewModel extends ViewModel {
    private CnbcRepository cnbcRepository;

    @Inject
    public CnbcViewModel(CnbcRepository cnbcRepository) {
        this.cnbcRepository = cnbcRepository;
    }

    public LiveData<ResponseModel> getDataTerbaru(){
        return cnbcRepository.getDataTerbaru();
    }

    public LiveData<ResponseModel> getDataNews(){
        return cnbcRepository.getDataNews();
    }

    public LiveData<ResponseModel> getDataMarket(){
        return cnbcRepository.getDataMarket();
    }
    public LiveData<ResponseModel> getDataEntrepreneur(){
        return cnbcRepository.getDataEnterpreneur();
    }

    public LiveData<ResponseModel> getDataSyariah(){
        return cnbcRepository.getDataSyariah();
    }

    public LiveData<ResponseModel> getDataTech(){
        return cnbcRepository.getDataTech();
    }

    public LiveData<ResponseModel> getDataLifeStyle(){
        return cnbcRepository.getDataLifeStyle();
    }

    public LiveData<ResponseModel> getDataOpini(){
        return cnbcRepository.getDataOpini();
    }

}
