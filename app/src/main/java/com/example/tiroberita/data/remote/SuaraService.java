package com.example.tiroberita.data.remote;

import com.example.tiroberita.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SuaraService {
    @GET("suara/terbaru/")
    Call<ResponseModel> getDataTerbaru();

    @GET("suara/bisnis/")
    Call<ResponseModel> getDataBisnis();

    @GET("suara/bola/")
    Call<ResponseModel> getDataBola();

    @GET("suara/lifestyle/")
    Call<ResponseModel> getDataLifeStyle();

    @GET("suara/entertainment/")
    Call<ResponseModel> getDataEntertainment();


    @GET("suara/otomotif/")
    Call<ResponseModel> getDataOtomotif();

    @GET("suara/tekno/")
    Call<ResponseModel> getDataTekno();

    @GET("suara/health/")
    Call<ResponseModel> getDataHealth();


}
