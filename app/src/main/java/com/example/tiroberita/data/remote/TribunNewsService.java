package com.example.tiroberita.data.remote;

import com.example.tiroberita.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TribunNewsService {
    @GET("tribun/terbaru/")
    Call<ResponseModel> getDataTerbaru();
}
