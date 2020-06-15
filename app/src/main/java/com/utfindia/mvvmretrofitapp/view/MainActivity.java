package com.utfindia.mvvmretrofitapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.utfindia.mvvmretrofitapp.R;
import com.utfindia.mvvmretrofitapp.model.CountryModel;
import com.utfindia.mvvmretrofitapp.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView listcountry;
    private TextView errorlist;
    private ProgressBar loading;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListViewModel listViewModel;
    private CountryListAdapter countryListAdapter = new CountryListAdapter(new ArrayList<CountryModel>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listcountry = findViewById(R.id.countrylist);
        errorlist = findViewById(R.id.error_list);
        loading = findViewById(R.id.loading_view);
        swipeRefreshLayout=findViewById(R.id.refreslayout);
        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        listViewModel.refresh();
        listcountry.setLayoutManager(new LinearLayoutManager(this));

        listcountry.setAdapter(countryListAdapter);
        swipeRefreshLayout.setOnRefreshListener(() -> {
         listViewModel.refresh();
         swipeRefreshLayout.setRefreshing(false);
        });
        observeViewModel();

    }

    private void observeViewModel() {
        listViewModel.countries.observe(this, countryModels -> {
            if (countryModels!=null){
                listcountry.setVisibility(View.VISIBLE);
                countryListAdapter.updateCountryList(countryModels);
            }

        });

        listViewModel.countryLoadError.observe(this, isError -> {
            if (isError!=null){
               errorlist.setVisibility(isError? View.VISIBLE:View.INVISIBLE);
            }
        });
        listViewModel.loading.observe(this, isLoading -> {
        if (isLoading!=null){
            loading.setVisibility(isLoading ? View.VISIBLE:View.INVISIBLE);
            if (isLoading){
                listcountry.setVisibility(View.GONE);
                errorlist.setVisibility(View.GONE);
            }
        }
        });
    }
}
