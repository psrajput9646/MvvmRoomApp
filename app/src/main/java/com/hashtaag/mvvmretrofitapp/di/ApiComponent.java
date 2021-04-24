package com.hashtaag.mvvmretrofitapp.di;

import com.hashtaag.mvvmretrofitapp.model.CountryServices;
import com.hashtaag.mvvmretrofitapp.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(CountryServices services);
    void injectMVClass(ListViewModel listViewModel);
}
