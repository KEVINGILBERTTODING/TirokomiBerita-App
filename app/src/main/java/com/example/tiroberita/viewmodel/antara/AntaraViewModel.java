package com.example.tiroberita.viewmodel.antara;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.antara.AntaraRepository;
import com.example.tiroberita.data.repository.kumparan.KumparanRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AntaraViewModel extends ViewModel {
    private AntaraRepository antaraRepository;

    @Inject
    public AntaraViewModel(AntaraRepository antaraRepository) {
        this.antaraRepository = antaraRepository;
    }

    public LiveData<ResponseModel> getDataTerbaru(){
        return antaraRepository.getDataTerbaru();
    }

    public LiveData<ResponseModel> getDataPolitik(){
        return antaraRepository.getDataPolitik();
    }

    public LiveData<ResponseModel> getDataHukum(){
        return antaraRepository.getDataHukum();
    }

    public LiveData<ResponseModel> getDataEkonomi(){
        return antaraRepository.getDataEkonomi();
    }

    public LiveData<ResponseModel> getDataBola(){
        return antaraRepository.getDataBola();
    }

    public LiveData<ResponseModel> getDataOlahraga(){
        return antaraRepository.getDataOlahraga();
    }

    public LiveData<ResponseModel> getDataHiburan(){
        return antaraRepository.getDataHiburan();
    }

    public LiveData<ResponseModel> getDataLifestyle(){
        return antaraRepository.getDataLifestyle();
    }

    public LiveData<ResponseModel> getDataHumaniora(){
        return antaraRepository.getDataHumaniora();
    }

    public LiveData<ResponseModel> getDataDunia(){
        return antaraRepository.getDataDunia();
    }

    public LiveData<ResponseModel> getDataTekno(){
        return antaraRepository.getDataTekno();
    }

    public LiveData<ResponseModel> getDataOtomotif(){
        return antaraRepository.getDataOtomotif();
    }


}
