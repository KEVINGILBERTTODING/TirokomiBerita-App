package com.example.tiroberita.data.repository.cnn;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tiroberita.data.remote.ApiService;
import com.example.tiroberita.data.remote.CnnApiService;
import com.example.tiroberita.model.ResponseModel;
import com.example.tiroberita.util.Constans;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CnnRepository {
    private CnnApiService cnnApiService;

    @Inject
    public CnnRepository(CnnApiService cnnApiService) {
        this.cnnApiService = cnnApiService;
    }

public LiveData<ResponseModel> getDataTerbaru(){
        MutableLiveData<ResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        cnnApiService.getDataTerbaru().enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        responseModelMutableLiveData.setValue(response.body());

                    }else {
                       responseModelMutableLiveData.setValue(new ResponseModel(false, Constans.ERR_MESSAGE, null));
                    }

                }else {
                    responseModelMutableLiveData.setValue(new ResponseModel(false, Constans.ERR_MESSAGE, null));


                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                responseModelMutableLiveData.setValue(new ResponseModel(false, Constans.ERR_MESSAGE, null));


            }
        });

        return responseModelMutableLiveData;
    }
}
