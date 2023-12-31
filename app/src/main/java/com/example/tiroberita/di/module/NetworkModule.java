package com.example.tiroberita.di.module;

import com.example.tiroberita.data.remote.AntaraService;
import com.example.tiroberita.data.remote.ApiService;
import com.example.tiroberita.data.remote.CnbcService;
import com.example.tiroberita.data.remote.CnnApiService;
import com.example.tiroberita.data.remote.JpnnService;
import com.example.tiroberita.data.remote.KumparanService;
import com.example.tiroberita.data.remote.OkezoneService;
import com.example.tiroberita.data.remote.RepublikaService;
import com.example.tiroberita.data.remote.SindonewsService;
import com.example.tiroberita.data.remote.SuaraService;
import com.example.tiroberita.data.remote.TempoService;
import com.example.tiroberita.data.remote.TribunNewsService;

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

    @Provides
    @Singleton
    public KumparanService kumparanService(Retrofit retrofit){
        return retrofit.create(KumparanService.class);
    }
    @Provides
    @Singleton
    public TribunNewsService tribunNewsService(Retrofit retrofit){
        return retrofit.create(TribunNewsService.class);
    }

    @Provides
    @Singleton
    public CnbcService cnbcService(Retrofit retrofit) {
        return retrofit.create(CnbcService.class);
    }

    @Provides
    @Singleton
    public OkezoneService okezoneService (Retrofit retrofit) {
        return retrofit.create(OkezoneService.class);
    }

    @Provides
    @Singleton
    public AntaraService antaraService (Retrofit retrofit) {
        return retrofit.create(AntaraService.class);
    }

    @Provides
    @Singleton
    public SindonewsService sindonewsService (Retrofit retrofit) {
        return retrofit.create(SindonewsService.class);

    }

    @Provides
    @Singleton
    public TempoService tempoService(Retrofit retrofit) {
        return retrofit.create(TempoService.class);
    }

    @Provides
    @Singleton
    public SuaraService suaraService (Retrofit retrofit) {
        return  retrofit.create(SuaraService.class);
    }

    @Provides
    @Singleton
    public RepublikaService republikaService (Retrofit retrofit) {
        return retrofit.create(RepublikaService.class);
    }

    @Provides
    @Singleton
    public JpnnService jpnnService(Retrofit retrofit){
        return retrofit.create(JpnnService.class);
    }
}
