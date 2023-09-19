package com.example.tiroberita.data.remote;

import com.example.tiroberita.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RepublikaService {
    @GET("republika/terbaru/")
    Call<ResponseModel> getDataTerbaru();

    @GET("republika/news/")
    Call<ResponseModel> getDataNews();

    @GET("republika/daerah/")
    Call<ResponseModel> getDataDaerah();

    @GET("republika/islam/")
    Call<ResponseModel> getDataIslam();

    @GET("republika/internasional/")
    Call<ResponseModel> getDataInternasional();

    @GET("republika/bola/")
    Call<ResponseModel> getDataBola();



}
