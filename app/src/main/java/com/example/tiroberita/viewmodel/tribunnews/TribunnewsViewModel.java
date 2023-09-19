package com.example.tiroberita.viewmodel.tribunnews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiroberita.data.repository.kumparan.KumparanRepository;
import com.example.tiroberita.data.repository.tribunnews.TribunnewsRepository;
import com.example.tiroberita.model.ResponseModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TribunnewsViewModel extends ViewModel {
    private TribunnewsRepository tribunnewsRepository;

    @Inject
    public TribunnewsViewModel(TribunnewsRepository tribunnewsRepository) {
        this.tribunnewsRepository = tribunnewsRepository;
    }

    public LiveData<ResponseModel> getDataTerbaru(){
        return tribunnewsRepository.getDataTerbaru();
    }


}
