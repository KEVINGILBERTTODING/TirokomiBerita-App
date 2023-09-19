package com.example.tiroberita.viewmodel.republika;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.kumparan.KumparanRepository;
import com.example.tiroberita.data.repository.republika.RepublikaRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RepublikaViewModel extends ViewModel {
    private RepublikaRepository republikaRepository;

    @Inject
    public RepublikaViewModel(RepublikaRepository republikaRepository){
        this.republikaRepository = republikaRepository;
    }

    public LiveData<ResponseModel> getDataTerbaru(){
        return republikaRepository.getDataTerbaru();
    }

    public LiveData<ResponseModel> getDataNews(){
        return republikaRepository.getDataNews();
    }

    public LiveData<ResponseModel> getDataDaerah(){
        return republikaRepository.getDataDaerah();
    }

    public LiveData<ResponseModel> getDataIslam(){
        return republikaRepository.getDataIslam();
    }

    public LiveData<ResponseModel> getDataInternasional(){
        return republikaRepository.getDataInternasional();
    }

    public LiveData<ResponseModel> getDataBola(){
        return republikaRepository.getDataBola();
    }


}
