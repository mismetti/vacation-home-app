package com.miladev.vacationhome.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miladev.vacationhome.R;
import com.miladev.vacationhome.activity.MainActivity;
import com.miladev.vacationhome.helper.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText edit_email;
    private EditText edit_password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        configClick();
        startComponents();
    }

    private void startComponents(){
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        progressBar = findViewById(R.id.progressBar);

    }

    public void validateDataLogin(View view){

        String email = edit_email.getText().toString();
        String password = edit_password.getText().toString();

        if(!email.isEmpty()){

            if (!password.isEmpty()){

                progressBar.setVisibility(View.VISIBLE);
                login(email, password);

            }else{
                edit_password.requestFocus();
                edit_password.setError("Password is empty");

            }

        }else{
            edit_email.requestFocus();
            edit_email.setError("Email is empty");
        }

    }

    private void login(String email, String password){

        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        finish();
                        startActivity(new Intent(this, MainActivity.class));
                    }else{
                        String error = task.getException().getMessage();
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

                    }
                    progressBar.setVisibility(View.GONE);

        });

    }

    private void configClick(){
        findViewById(R.id.create_account).setOnClickListener(view ->
                startActivity(new Intent(this, CreateAccountActivity.class)));

        findViewById(R.id.forgot_password).setOnClickListener(view ->
                startActivity(new Intent(this, PasswordRecoveryActivity.class)));
    }

}