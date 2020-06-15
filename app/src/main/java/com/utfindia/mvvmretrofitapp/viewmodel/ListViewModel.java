package com.utfindia.mvvmretrofitapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utfindia.mvvmretrofitapp.di.DaggerApiComponent;
import com.utfindia.mvvmretrofitapp.model.CountryModel;
import com.utfindia.mvvmretrofitapp.model.CountryServices;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<List<CountryModel>>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public CountryServices countryServices ;

    public ListViewModel() {
        super();
        DaggerApiComponent.create().injectMVClass(this);
    }

    public void refresh() {
        fetchCountries();
    }

    private void fetchCountries() {
        loading.setValue(true);
        compositeDisposable.add(
                countryServices.getCountries()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {
                            @Override
                           public void onSuccess(List<CountryModel> countryModels) {
                           countries.setValue(countryModels);
                           countryLoadError.setValue(false);
                           loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                countryLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
