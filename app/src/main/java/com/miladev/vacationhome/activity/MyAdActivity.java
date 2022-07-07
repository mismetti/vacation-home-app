package com.miladev.vacationhome.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class MyAdActivity extends AppCompatActivity implements AdAdapter.OnClick {

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
        configClick();


    }

    @Override
    protected void onStart() {
        super.onStart();

        recoveryAds();
    }

    private void configClick(){
        findViewById(R.id.ib_Add).setOnClickListener(view -> startActivity(new Intent(this, AdFormActivity.class))
        );

        findViewById(R.id.imageB_back).setOnClickListener(view -> finish());
    }

    private void configRv(){
        rv_ads.setLayoutManager(new LinearLayoutManager(this));
        rv_ads.setHasFixedSize(true);
        adAdapter = new AdAdapter(adList, this);
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
                    text_alert.setVisibility(View.GONE);
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

    @Override
    public void OnClickListener(Ad ad) {
        Intent intent = new Intent(this, AdFormActivity.class);
        intent.putExtra("ad",ad);
        startActivity(intent);
    }
}