package com.hashtaag.mvvmretrofitapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hashtaag.mvvmretrofitapp.R;
import com.hashtaag.mvvmretrofitapp.room.Note;
import com.hashtaag.mvvmretrofitapp.viewmodel.ListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "tag";
    private ImageView imageView;
    private TextView errorlist;
    private ProgressBar loading;
    private ListViewModel listViewModel;
    private Button mButtons;
    private boolean status = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.countrylist);
        errorlist = findViewById(R.id.error_list);
        loading = findViewById(R.id.loading_view);
        mButtons = findViewById(R.id.mbutton);
        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        listViewModel.refresh();

        mButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observeViewModel();
            }
        });

    }

    private void observeViewModel() {
        listViewModel.countries.observe(this, countryModels -> {
            if (countryModels != null) {

                imageView.setVisibility(View.VISIBLE);
                status = searchDuplicate(countryModels.getPath());
                DownloadImageFromPath(countryModels.getPath());
                //listViewModel.insert(new Note(countryModels.getPath()));
                if (status) {
                    // Toast.makeText(this, " Data Hai "+status, Toast.LENGTH_SHORT).show();
                    listViewModel.insert(new Note(countryModels.getPath()));
                }
            }

        });

        listViewModel.countryLoadError.observe(this, isError -> {
            if (isError != null) {
                errorlist.setVisibility(isError ? View.VISIBLE : View.INVISIBLE);
            }
        });
        listViewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                loading.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
                if (isLoading) {
                    imageView.setVisibility(View.GONE);
                    errorlist.setVisibility(View.GONE);
                }
            }
        });

    }

    private Boolean searchDuplicate(String path) {

        listViewModel.getAllnotes().observe(MainActivity.this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                String name = "";
               if(notes.size()==0){
                   status=true;
               }else {
                   for (int i = 0; i < notes.size() - 1; i++) {
                       name+= notes.get(i).getId()  + " " + notes.get(i).getTitle()+"\n";
                       if (path.equals(notes.get(i).getTitle())) {
                           status = false;
                           Toast.makeText(MainActivity.this, "Already Data Exist in DB...", Toast.LENGTH_SHORT).show();
                           break;
                       } else {
                           status = true;
                       }
                   }
               }


                Log.d(TAG, "onChanged: " +name+ status);
            }
        });
        return status;

    }

    private void DownloadImageFromPath(String path) {

        Glide.with(this)
                .asBitmap()
                .load(path)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                    }
                });

    }


}
