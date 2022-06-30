package com.miladev.vacationhome.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.miladev.vacationhome.R;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        configClick();
        startComponents();
    }

    private void configClick(){
        findViewById(R.id.imageB_voltar).setOnClickListener(view -> finish());
    }

    private void startComponents(){
        TextView text_titulo = findViewById(R.id.text_titulo_toolbar);
        text_titulo.setText("Create your Account");
    }

}