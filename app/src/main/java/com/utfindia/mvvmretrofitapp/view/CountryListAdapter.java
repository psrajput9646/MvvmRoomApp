package com.utfindia.mvvmretrofitapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utfindia.mvvmretrofitapp.R;
import com.utfindia.mvvmretrofitapp.model.CountryModel;

import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.DemoHolder> {
    private List<CountryModel> list;

    public CountryListAdapter(List<CountryModel> listcu) {
        this.list = listcu;
    }

    public void updateCountryList(List<CountryModel> updatelist) {
        list.clear();
        list.addAll(updatelist);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DemoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new DemoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DemoHolder extends RecyclerView.ViewHolder {
        private ImageView countryimg;
        private TextView countrynm;
        private TextView captialnm;

        public DemoHolder(@NonNull View itemView) {
            super(itemView);
            countrynm = itemView.findViewById(R.id.countryname);
            captialnm = itemView.findViewById(R.id.captialname);
            countryimg = itemView.findViewById(R.id.img);
        }

        void bind(CountryModel countryModel) {
            countrynm.setText(countryModel.getCountryName());
            captialnm.setText(countryModel.getCapital());
            Util.loadImage(countryimg,countryModel.getFlag(),Util.setProgress(countryimg.getContext()));
        }
    }

   /* public class DemoHolder extends RecyclerView.ViewHolder {


        public Holder(@NonNull View itemView) {
            super(itemView);
            countrynm = itemView.findViewById(R.id.countryname);
            captialnm = itemView.findViewById(R.id.captialname);
            countryimg = itemView.findViewById(R.id.img);

        }

        public void bind(CountryModel countryModel) {
            countrynm.setText(countryModel.getCountryName());
            captialnm.setText(countryModel.getCapital());
        }
    }*/
}
