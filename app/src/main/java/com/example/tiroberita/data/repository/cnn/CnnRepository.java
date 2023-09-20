package com.example.tiroberita.data.repository.cnn;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tiroberita.data.remote.CnnApiService;
import com.example.tiroberita.model.ResponseModel;
import com.example.tiroberita.util.constans.Constans;

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
                responseModelMutableLiveData.setValue(new ResponseModel(false, Constans.NO_INTERNET_CONNECTION, null));


            }
        });

        return responseModelMutableLiveData;
    }

    public LiveData<ResponseModel> getDataNasional(){
        MutableLiveData<ResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        cnnApiService.getDataNasional().enqueue(new Callback<ResponseModel>() {
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


    public LiveData<ResponseModel> getDataOlahraga(){
        MutableLiveData<ResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        cnnApiService.getDataOlahraga().enqueue(new Callback<ResponseModel>() {
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

    public LiveData<ResponseModel> getDataTeknologi(){
        MutableLiveData<ResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        cnnApiService.getDataTeknologi().enqueue(new Callback<ResponseModel>() {
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

    public LiveData<ResponseModel> getDataEkonomi(){
        MutableLiveData<ResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        cnnApiService.getDataEkonomi().enqueue(new Callback<ResponseModel>() {
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

    public LiveData<ResponseModel> getDataInternasional(){
        MutableLiveData<ResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        cnnApiService.getDataInternasional().enqueue(new Callback<ResponseModel>() {
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

    public LiveData<ResponseModel> getDataHiburan(){
        MutableLiveData<ResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        cnnApiService.getDataHiburan().enqueue(new Callback<ResponseModel>() {
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

    public LiveData<ResponseModel> getDataGayaHidup(){
        MutableLiveData<ResponseModel> responseModelMutableLiveData = new MutableLiveData<>();
        cnnApiService.getDataGayaHidup().enqueue(new Callback<ResponseModel>() {
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
