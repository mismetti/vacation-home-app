package com.miladev.vacationhome.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.miladev.vacationhome.R;

public class PasswordRecoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        startComponents();
        configClick();

    }

    private void configClick(){
        findViewById(R.id.imageB_back).setOnClickListener(view -> finish());
    }

    private void startComponents(){
        TextView text_title = findViewById(R.id.text_title_toolbar);
        text_title.setText("Recovery your account");
    }

}