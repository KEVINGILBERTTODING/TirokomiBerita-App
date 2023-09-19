package com.example.tiroberita.data.remote;

import com.example.tiroberita.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface KumparanService {
    @GET("kumparan/terbaru/")
    Call<ResponseModel> getDataTerbaru();
}
