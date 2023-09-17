package com.example.tiroberita.di.module;

import com.example.tiroberita.data.remote.ApiService;
import com.example.tiroberita.data.remote.CnnApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    @Provides
    @Singleton
    public CnnApiService cnnApiService(Retrofit retrofit) {
        return retrofit.create(CnnApiService.class);
    }
}
