package com.example.tiroberita.data.repository.kumparan;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tiroberita.data.remote.KumparanService;
import com.example.tiroberita.model.ResponseModel;
import com.example.tiroberita.util.constans.Constans;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KumparanRepository {
    private KumparanService kumparanService;

    @Inject
    public KumparanRepository(KumparanService kumparanService) {
        this.kumparanService = kumparanService;
    }

public LiveData<ResponseModel> getDataTerbaru(){
        MutableLiveData<ResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        kumparanService.getDataTerbaru().enqueue(new Callback<ResponseModel>() {
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
                responseModelMutableLiveData.setValue(new ResponseModel(false, Constans.NO_INTERNET_CONNECTION, null));


            }
        });

        return responseModelMutableLiveData;
    }

}
