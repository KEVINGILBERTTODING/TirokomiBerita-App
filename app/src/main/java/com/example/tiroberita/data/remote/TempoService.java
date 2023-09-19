package com.example.tiroberita.data.remote;

import com.example.tiroberita.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TempoService {


    @GET("tempo/nasional/")
    Call<ResponseModel> getDataNasional();

    @GET("tempo/bisnis/")
    Call<ResponseModel> getDataBisnis();

    @GET("tempo/metro/")
    Call<ResponseModel> getDataMetro();


    @GET("tempo/dunia/")
    Call<ResponseModel> getDataDunia();

    @GET("tempo/bola/")
    Call<ResponseModel> getDataBola();

    @GET("tempo/cantik/")
    Call<ResponseModel> getDataCantik();

    @GET("tempo/otomotif/")
    Call<ResponseModel> getDataOtomotif();

    @GET("tempo/tekno/")
    Call<ResponseModel> getDataTekno();

    @GET("tempo/seleb/")
    Call<ResponseModel> getDataSeleb();


    @GET("tempo/gaya/")
    Call<ResponseModel> getDataGaya();

    @GET("tempo/travel/")
    Call<ResponseModel> getDataTravel();

    @GET("tempo/difabel/")
    Call<ResponseModel> getDataDifabel();

    @GET("tempo/creativelab/")
    Call<ResponseModel> getDataCreativeLab();

    @GET("tempo/inforial/")
    Call<ResponseModel> getDataInforial();

    @GET("tempo/event/")
    Call<ResponseModel> getDataEvent();



}
