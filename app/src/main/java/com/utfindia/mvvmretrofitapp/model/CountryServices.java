package com.utfindia.mvvmretrofitapp.model;

import com.utfindia.mvvmretrofitapp.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryServices {
    private static CountryServices instance;
    @Inject
    public CountriesApi api;

    private CountryServices() {

        DaggerApiComponent.create().inject(this);
    }

    public static CountryServices getInstance() {
        if (instance == null) {
            instance = new CountryServices();
        }
        return instance;
    }


    public Single<List<CountryModel>> getCountries() {
        return api.getCountries();
    }
}
