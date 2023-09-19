package com.example.tiroberita.data.remote;

import com.example.tiroberita.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SindonewsService {
    @GET("sindonews/terbaru/")
    Call<ResponseModel> getDataTerbaru();

    @GET("sindonews/nasional/")
    Call<ResponseModel> getDataNasional();

    @GET("sindonews/metro/")
    Call<ResponseModel> getDataMetro();

    @GET("sindonews/sports/")
    Call<ResponseModel> getDataSports();

    @GET("sindonews/ekbis/")
    Call<ResponseModel> getDataEkbis();

    @GET("sindonews/international/")
    Call<ResponseModel> getDataInternational();

    @GET("sindonews/daerah/")
    Call<ResponseModel> getDataDaerah();


    @GET("sindonews/tekno/")
    Call<ResponseModel> getDataTekno();

    @GET("sindonews/sains/")
    Call<ResponseModel> getDataSains();

    @GET("sindonews/edukasi/")
    Call<ResponseModel> getDataEdukasi();

    @GET("sindonews/lifestyle/")
    Call<ResponseModel> getDataLifestyle();

    @GET("sindonews/kalam/")
    Call<ResponseModel> getDataKalam();


}
