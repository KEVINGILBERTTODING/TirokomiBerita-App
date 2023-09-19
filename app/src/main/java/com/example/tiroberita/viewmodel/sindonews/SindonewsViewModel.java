package com.example.tiroberita.viewmodel.sindonews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.tiroberita.data.repository.sindonews.SindoNewsRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.http.GET;

@HiltViewModel
public class SindonewsViewModel extends ViewModel {
    private SindoNewsRepository sindoNewsRepository;

    @Inject
    public SindonewsViewModel(SindoNewsRepository sindoNewsRepository){

        this.sindoNewsRepository = sindoNewsRepository;
    }

    public LiveData<ResponseModel> getDataTerbaru(){
        return sindoNewsRepository.getDataTerbaru();
    }

    public LiveData<ResponseModel> getDataNasional(){
        return sindoNewsRepository.getDataNasional();
    }

    public LiveData<ResponseModel> getDataMetro(){
        return sindoNewsRepository.getDataMetro();
    }

    public LiveData<ResponseModel> getDataSports(){
        return sindoNewsRepository.getDataSports();
    }

    public LiveData<ResponseModel> getDataEkbis(){
        return sindoNewsRepository.getDataEkbis();
    }

    public LiveData<ResponseModel> getDataInternational(){
        return sindoNewsRepository.getDataInternational();
    }

    public LiveData<ResponseModel> getDataDaerah(){
        return sindoNewsRepository.getDataDaerah();
    }

    public LiveData<ResponseModel> getDataTekno(){
        return sindoNewsRepository.getDataTekno();
    }

    public LiveData<ResponseModel> getDataSains(){
        return sindoNewsRepository.getDataSains();
    }

    public LiveData<ResponseModel> getDataEdukasi(){
        return sindoNewsRepository.getDataEdukasi();
    }

    public LiveData<ResponseModel> getDataLifestyle(){
        return sindoNewsRepository.getDataLifestyle();
    }

    public LiveData<ResponseModel> getDataKalam(){
        return sindoNewsRepository.getDataKalam();
    }





}
