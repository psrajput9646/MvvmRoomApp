package com.utfindia.mvvmretrofitapp.di;

import com.utfindia.mvvmretrofitapp.model.CountryServices;
import com.utfindia.mvvmretrofitapp.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(CountryServices services);
    void injectMVClass(ListViewModel listViewModel);
}
