package com.hashtaag.mvvmretrofitapp.model;

import com.hashtaag.mvvmretrofitapp.di.DaggerApiComponent;

import javax.inject.Inject;

import io.reactivex.Single;

public class CountryServices {
    private static CountryServices instance;
    @Inject
    public CountriesApi  api;

    private CountryServices() {
     DaggerApiComponent.create().inject(this);
    }

    public static CountryServices getInstance() {
        if (instance == null) {
            instance = new CountryServices();
        }
        return instance;
    }
    public Single<Response> getResponse() {
        return api.getResponse();
    }
}
