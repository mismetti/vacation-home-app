package com.miladev.vacationhome.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.miladev.vacationhome.R;
import com.miladev.vacationhome.model.Product;

public class AdFormActivity extends AppCompatActivity {

    private EditText ad_title;
    private EditText ad_desc;
    private EditText ad_room;
    private EditText ad_bath;
    private EditText ad_garage;
    private CheckBox check_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_form);

        startComponents();
        configClick();
    }

    private void configClick(){
        findViewById(R.id.save).setOnClickListener(view -> validateData());
        findViewById(R.id.imageB_back).setOnClickListener(view -> finish());
    }

    private void startComponents(){

        ad_title = findViewById(R.id.ad_title);
        ad_desc = findViewById(R.id.ad_desc);
        ad_room = findViewById(R.id.ad_room);
        ad_bath = findViewById(R.id.ad_bath);
        ad_garage = findViewById(R.id.qt_garage);
        check_available = findViewById(R.id.check_available);

        TextView text_titulo = findViewById(R.id.text_title_toolbar);
        text_titulo.setText("New Ad");

    }

    private void validateData(){
        String title = ad_title.getText().toString();
        String desc = ad_desc.getText().toString();
        String room = ad_room.getText().toString();
        String bath = ad_bath.getText().toString();
        String garage = ad_garage.getText().toString();

        if (!title.isEmpty()){

            if (!desc.isEmpty()){

                if (!room.isEmpty()){

                    if (!bath.isEmpty()){

                        if (!garage.isEmpty()){

                            Product product = new Product();
                            product.setTitle(title);
                            product.setDescription(desc);
                            product.setRoom(room);
                            product.setBathroom(bath);
                            product.setGarage(garage);
                            product.setStatus(check_available.isChecked());

                        }else{
                            ad_garage.requestFocus();
                            ad_garage.setError("Quantity of garages not defined.");
                        }
                    }else{
                        ad_bath.requestFocus();
                        ad_bath.setError("Quantity of bathrooms not defined.");
                    }
                }else{
                    ad_room.requestFocus();
                    ad_room.setError("Quantity of rooms not defined.");
                }
            }else{
                ad_desc.requestFocus();
                ad_desc.setError("Description is empty.");
            }
        }else{
            ad_title.requestFocus();
            ad_title.setError("Title is empty.");
        }

    }

}