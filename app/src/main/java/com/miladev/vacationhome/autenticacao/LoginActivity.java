package com.miladev.vacationhome.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.miladev.vacationhome.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        configClick();
    }

    private void configClick(){
        findViewById(R.id.create_account).setOnClickListener(view ->
                startActivity(new Intent(this, CreateAccountActivity.class)));

        findViewById(R.id.forgot_password).setOnClickListener(view ->
                startActivity(new Intent(this, PasswordRecoveryActivity.class)));
    }
}