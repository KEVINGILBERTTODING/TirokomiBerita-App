package com.example.tiroberita.viewmodel.jpnn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.tiroberita.data.repository.jpnn.JpnnRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class JpnnViewModel extends ViewModel {
    private JpnnRepository jpnnRepository;

    @Inject
    public JpnnViewModel(JpnnRepository jpnnRepository){
        this.jpnnRepository = jpnnRepository;
    }

    public LiveData<ResponseModel> getDataTerbaru(){
        return jpnnRepository.getDataTerbaru();
    }


}
