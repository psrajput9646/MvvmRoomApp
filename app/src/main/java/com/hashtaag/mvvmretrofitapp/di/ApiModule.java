package com.hashtaag.mvvmretrofitapp.di;

import com.hashtaag.mvvmretrofitapp.model.CountriesApi;
import com.hashtaag.mvvmretrofitapp.model.CountryServices;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {


   private static final String BASE_URL = "http://203.90.77.58:3004";
   //http://203.90.77.58:3004/image/test
    @Provides
    public CountriesApi providerApi() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CountriesApi.class);
    }

    @Provides
    public CountryServices countryServices(){
        return  CountryServices.getInstance();
    }

}
