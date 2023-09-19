package com.example.tiroberita.data.remote;

import com.example.tiroberita.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AntaraService {
    @GET("antara/terbaru/")
    Call<ResponseModel> getDataTerbaru();

    @GET("antara/politik/")
    Call<ResponseModel> getDataPolitik();


    @GET("antara/ekonomi/")
    Call<ResponseModel> getDataEkonomi();

    @GET("antara/bola/")
    Call<ResponseModel> getDataBola();

    @GET("antara/olahraga/")
    Call<ResponseModel> getDataOlahraga();


    @GET("antara/tekno/")
    Call<ResponseModel> getDataTekno();

    @GET("antara/otomotif/")
    Call<ResponseModel> getDataOtomotif();

    @GET("antara/hiburan/")
    Call<ResponseModel> getDataHiburan();

    @GET("antara/lifestyle/")
    Call<ResponseModel> getDataLifestyle();


    @GET("antara/hukum/")
    Call<ResponseModel> getDataHukum();

    @GET("antara/humaniora/")
    Call<ResponseModel> getDataHumaniora();

    @GET("antara/dunia/")
    Call<ResponseModel> getDataDunia();


}
