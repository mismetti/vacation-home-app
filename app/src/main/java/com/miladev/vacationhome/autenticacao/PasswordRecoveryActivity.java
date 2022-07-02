package com.miladev.vacationhome.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.miladev.vacationhome.R;
import com.miladev.vacationhome.helper.FirebaseHelper;

public class PasswordRecoveryActivity extends AppCompatActivity {

    private EditText edit_email;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        startComponents();
        configClick();

    }

    public void validateDataRecovery(View view){
        String email = edit_email.getText().toString();

        if(!email.isEmpty()){

            progressBar.setVisibility(View.VISIBLE);
            recoveryPassword(email);

        }else{
            edit_email.requestFocus();
            edit_email.setError("Email is empty.");
        }
    }

    private void recoveryPassword(String email){

        FirebaseHelper.getAuth().sendPasswordResetEmail(
                email
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this, "Sent successfully", Toast.LENGTH_SHORT).show();
            }else{
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    private void configClick(){
        findViewById(R.id.imageB_back).setOnClickListener(view -> finish());
    }

    private void startComponents(){

        edit_email = findViewById(R.id.edit_email);
        progressBar = findViewById(R.id.progressBar);

        //setting a new name to toolbar
        TextView text_title = findViewById(R.id.text_title_toolbar);
        text_title.setText("Recovery your account");
    }

}