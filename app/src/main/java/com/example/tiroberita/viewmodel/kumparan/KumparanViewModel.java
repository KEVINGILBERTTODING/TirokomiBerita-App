package com.example.tiroberita.viewmodel.kumparan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.remote.KumparanService;
import com.example.tiroberita.data.repository.cnn.CnnRepository;
import com.example.tiroberita.data.repository.kumparan.KumparanRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class KumparanViewModel extends ViewModel {
    private KumparanRepository kumparanRepository;

    @Inject
    public KumparanViewModel(KumparanRepository kumparanRepository) {
        this.kumparanRepository = kumparanRepository;
    }

    public LiveData<ResponseModel> getDataTerbaru(){
        return kumparanRepository.getDataTerbaru();
    }


}
