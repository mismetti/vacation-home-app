package com.miladev.vacationhome.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miladev.vacationhome.R;
import com.miladev.vacationhome.model.Ad;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.MyViewHolder> {

    private List<Ad> adList;

    public AdAdapter(List<Ad> adList) {
        this.adList = adList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Ad ad = adList.get(position);
        Picasso.get().load(ad.getUrlImage()).into(holder.img_ad);
        holder.text_title.setText(ad.getTitle());
        holder.text_desc.setText(ad.getTitle());
        holder.text_date.setText("");
        holder.text_price.setText(ad.getPrice());

    }

    @Override
    public int getItemCount() {
        return adList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text_title, text_desc, text_date, text_price;
        ImageView img_ad;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_ad = itemView.findViewById(R.id.img_ad);
            text_title = itemView.findViewById(R.id.title_ad);
            text_desc = itemView.findViewById(R.id.desc_ad);
            text_date = itemView.findViewById(R.id.date_ad);
            text_price = itemView.findViewById(R.id.price_ad);



        }
    }
}
