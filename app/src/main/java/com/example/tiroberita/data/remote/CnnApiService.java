package com.example.tiroberita.data.remote;

import com.example.tiroberita.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CnnApiService {
    @GET("cnn/terbaru/")
    Call<ResponseModel> getDataTerbaru();

    @GET("cnn/nasional/")
    Call<ResponseModel> getDataNasional();

    @GET("cnn/internasional/")
    Call<ResponseModel> getDataInternasional();

    @GET("cnn/ekonomi/")
    Call<ResponseModel> getDataEkonomi();

    @GET("cnn/olahraga/")
    Call<ResponseModel> getDataOlahraga();

    @GET("cnn/teknologi/")
    Call<ResponseModel> getDataTeknologi();

    @GET("cnn/hiburan/")
    Call<ResponseModel> getDataHiburan();

    @GET("cnn/gayaHidup/")
    Call<ResponseModel> getDataGayaHidup();
}
