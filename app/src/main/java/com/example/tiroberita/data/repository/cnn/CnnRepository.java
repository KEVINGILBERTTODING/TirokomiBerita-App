package com.example.tiroberita.data.repository.cnn;


import com.example.tiroberita.data.remote.ApiService;

import javax.inject.Inject;


public class CnnRepository {
    private ApiService apiService;

    @Inject
    public CnnRepository(ApiService apiService) {
        this.apiService = apiService;
    }

}
