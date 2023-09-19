package com.example.tiroberita.data.remote;

import com.example.tiroberita.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OkezoneService {
    @GET("okezone/terbaru/")
    Call<ResponseModel> getDataTerbaru();

    @GET("okezone/celebrity/")
    Call<ResponseModel> getDataCelebrity();


    @GET("okezone/sports/")
    Call<ResponseModel> getDataSports();

    @GET("okezone/otomotif/")
    Call<ResponseModel> getDataOtomotif();

    @GET("okezone/economy/")
    Call<ResponseModel> getDataEconomy();

    @GET("okezone/techno/")
    Call<ResponseModel> getDataTechno();

    @GET("okezone/lifestyle/")
    Call<ResponseModel> getDataLifestyle();

    @GET("okezone/bola/")
    Call<ResponseModel> getDataBola();
}
