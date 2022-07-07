package com.miladev.vacationhome.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.miladev.vacationhome.R;
import com.miladev.vacationhome.adapter.AdAdapter;
import com.miladev.vacationhome.helper.FirebaseHelper;
import com.miladev.vacationhome.model.Ad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyAdActivity extends AppCompatActivity {

    private List<Ad> adList = new ArrayList<>();

    private ProgressBar progressBar;
    private TextView text_alert;
    private RecyclerView rv_ads;
    private AdAdapter adAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ad);

        startComponents();
        configRv();
        recoveryAds();


    }
    private void configRv(){
        rv_ads.setLayoutManager(new LinearLayoutManager(this));
        rv_ads.setHasFixedSize(true);
        adAdapter = new AdAdapter(adList);
        rv_ads.setAdapter(adAdapter);
    }

    private void recoveryAds(){

        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("ad")
                .child(FirebaseHelper.getFirebaseId());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    adList.clear();
                    for (DataSnapshot snap : snapshot.getChildren()){
                        Ad ad = snap.getValue(Ad.class);
                        adList.add(ad);
                    }
                    text_alert.setText("");
                }else{
                    text_alert.setText("No ads registered...");
                }
                progressBar.setVisibility(View.GONE);

                Collections.reverse(adList);
                adAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void startComponents(){
        TextView text_title = findViewById(R.id.text_title_toolbar);
        text_title.setText("My Ads");

        progressBar = findViewById(R.id.progressBar);
        text_alert = findViewById(R.id.text_alert);
        rv_ads = findViewById(R.id.rv_ads);

    }
}