package com.example.tiroberita.data.remote;

import com.example.tiroberita.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CnbcService {
    @GET("cnbc/terbaru/")
    Call<ResponseModel> getDataTerbaru();

    @GET("cnbc/news/")
    Call<ResponseModel> getDataNews();

    @GET("cnbc/market/")
    Call<ResponseModel> getDataMarket();

    @GET("cnbc/entrepreneur/")
    Call<ResponseModel> getDataEnterpreneur();

    @GET("cnbc/syariah/")
    Call<ResponseModel> getDataSyariah();

    @GET("cnbc/tech/")
    Call<ResponseModel> getDataTech();

    @GET("cnbc/lifestyle/")
    Call<ResponseModel> getDataLifeStyle();

    @GET("cnbc/opini/")
    Call<ResponseModel> getDataOpini();

}
