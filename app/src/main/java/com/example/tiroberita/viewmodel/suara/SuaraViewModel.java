package com.example.tiroberita.viewmodel.suara;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.kumparan.KumparanRepository;
import com.example.tiroberita.data.repository.suara.SuaraRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.http.GET;

@HiltViewModel
public class SuaraViewModel extends ViewModel {
    private SuaraRepository suaraRepository;

    @Inject
    public SuaraViewModel(SuaraRepository suaraRepository){
        this.suaraRepository = suaraRepository;
    }

    public LiveData<ResponseModel> getDataTerbaru(){
        return suaraRepository.getDataTerbaru();
    }

    public LiveData<ResponseModel> getDataBisnis(){
        return suaraRepository.getDataBisnis();
    }

    public LiveData<ResponseModel> getDataBola(){
        return suaraRepository.getDataBola();
    }

    public LiveData<ResponseModel> getDataLifeStyle(){
        return suaraRepository.getDataLifeStyle();
    }

    public LiveData<ResponseModel> getDataEntertainment(){
        return suaraRepository.getDataEntertainment();
    }

    public LiveData<ResponseModel> getDataOtomotif(){
        return suaraRepository.getDataOtomotif();
    }

    public LiveData<ResponseModel> getDataTekno(){
        return suaraRepository.getDataTekno();
    }

    public LiveData<ResponseModel> getDataHealth(){
        return suaraRepository.getDataHealth();
    }





}
