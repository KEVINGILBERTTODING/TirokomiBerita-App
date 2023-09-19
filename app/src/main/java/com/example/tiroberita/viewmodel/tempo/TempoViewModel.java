package com.example.tiroberita.viewmodel.tempo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.kumparan.KumparanRepository;
import com.example.tiroberita.data.repository.tempoRepository.TempoRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TempoViewModel extends ViewModel {
    private TempoRepository tempoRepository;

    @Inject
    public TempoViewModel(TempoRepository tempoRepository){

        this.tempoRepository = tempoRepository;
    }



    public LiveData<ResponseModel> getDataNasional(){
        return tempoRepository.getDataNasional();
    }

    public LiveData<ResponseModel> getDataBisnis(){
        return tempoRepository.getDataBisnis();
    }

    public LiveData<ResponseModel> getDataMetro(){
        return tempoRepository.getDataMetro();
    }

    public LiveData<ResponseModel> getDataDunia(){
        return tempoRepository.getDataDunia();
    }

    public LiveData<ResponseModel> getDataBola(){
        return tempoRepository.getDataBola();
    }

    public LiveData<ResponseModel> getDataCantik(){
        return tempoRepository.getDataCantik();
    }

    public LiveData<ResponseModel> getDataOtomotif(){
        return tempoRepository.getDataOtomotif();
    }

    public LiveData<ResponseModel> getDataTekno(){
        return tempoRepository.getDataTekno();
    }

    public LiveData<ResponseModel> getDataSeleb(){
        return tempoRepository.getDataSeleb();
    }

    public LiveData<ResponseModel> getDataTravel(){
        return tempoRepository.getDataTravel();
    }

    public LiveData<ResponseModel> getDataGaya(){
        return tempoRepository.getDataGaya();
    }


    public LiveData<ResponseModel> getDataCreativeLab(){
        return tempoRepository.getDataCreativeLab();
    }

    public LiveData<ResponseModel> getDataDifabel(){
        return tempoRepository.getDataDifabel();
    }

    public LiveData<ResponseModel> getDataInforial(){
        return tempoRepository.getDataInforial();
    }

    public LiveData<ResponseModel> getDataEvent(){
        return tempoRepository.getDataEvent();
    }



}
