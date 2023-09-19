package com.example.tiroberita.viewmodel.okezone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.okezone.OkezoneRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OkezoneViewModel extends ViewModel {
    private OkezoneRepository okezoneRepository;

    @Inject
    public OkezoneViewModel(OkezoneRepository okezoneRepository) {
        this.okezoneRepository = okezoneRepository;
    }

    public LiveData<ResponseModel> getDataTerbaru(){
        return okezoneRepository.getDataTerbaru();
    }

    public LiveData<ResponseModel> getDataCelebrity(){
        return okezoneRepository.getDataCelebrity();
    }


    public LiveData<ResponseModel> getDataSports(){
        return okezoneRepository.getDataSports();
    }

    public LiveData<ResponseModel> getDataOtomotif(){
        return okezoneRepository.getDataOtomotif();
    }

    public LiveData<ResponseModel> getDataEconomy(){
        return okezoneRepository.getDataEconomy();
    }


    public LiveData<ResponseModel> getDataTechno(){
        return okezoneRepository.getDataTechno();
    }

    public LiveData<ResponseModel> getDataLifestyle(){
        return okezoneRepository.getDataLifestyle();
    }

    public LiveData<ResponseModel> getDataBola(){
        return okezoneRepository.getDataBola();
    }



}
