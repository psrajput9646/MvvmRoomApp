package com.hashtaag.mvvmretrofitapp.model;

import io.reactivex.Single;
import retrofit2.http.POST;

public interface CountriesApi {


    //http://203.90.77.58:3004/image/test
    @POST("/image/test")
    Single<Response> getResponse();

}
