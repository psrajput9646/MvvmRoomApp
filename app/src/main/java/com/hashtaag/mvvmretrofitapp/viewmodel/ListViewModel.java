package com.hashtaag.mvvmretrofitapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.hashtaag.mvvmretrofitapp.di.DaggerApiComponent;
import com.hashtaag.mvvmretrofitapp.model.CountryServices;
import com.hashtaag.mvvmretrofitapp.model.Response;
import com.hashtaag.mvvmretrofitapp.room.Note;
import com.hashtaag.mvvmretrofitapp.room.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {


    public MutableLiveData<Response> countries = new MutableLiveData<Response>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allnotes;

    @Inject
    public CountryServices countryServices ;

    public ListViewModel(Application application) {
        super(application);

        DaggerApiComponent.create().injectMVClass(this);
        noteRepository = new NoteRepository(application);
        this.allnotes = noteRepository.getAllnotes();
    }

    public void refresh() {
        fetchCountries();

    }

    private void fetchCountries() {
        loading.setValue(true);
        compositeDisposable.add(
                countryServices.getResponse()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Response>() {
                            @Override
                            public void onSuccess(Response countryModels) {
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

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public void deleteAllNotes() {
        noteRepository.deleteAll();
    }

    public LiveData<List<Note>> getAllnotes() {
        return allnotes;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
