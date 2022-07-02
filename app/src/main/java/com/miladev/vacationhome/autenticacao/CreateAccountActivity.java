package com.miladev.vacationhome.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.miladev.vacationhome.R;
import com.miladev.vacationhome.activity.MainActivity;
import com.miladev.vacationhome.helper.FirebaseHelper;
import com.miladev.vacationhome.model.User;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText edit_fullname;
    private EditText edit_email;
    private EditText edit_phone;
    private EditText edit_password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        configClick();
        startComponents();
    }

    public void validateDataCreateAccount(View view){
        String fullname = edit_fullname.getText().toString();
        String email = edit_email.getText().toString();
        String phone = edit_phone.getText().toString();
        String password = edit_password.getText().toString();

        if(!fullname.isEmpty()){

            if(!email.isEmpty()){

                if(!phone.isEmpty()){

                    if(!password.isEmpty()){

                        progressBar.setVisibility(View.VISIBLE);

                        User user = new User();
                        user.setFullname(fullname);
                        user.setEmail(email);
                        user.setPhone(phone);
                        user.setPassword(password);

                        registerUser(user);

                    }else{
                        edit_password.requestFocus();
                        edit_password.setError("Password is empty.");
                    }

                }else{
                    edit_phone.requestFocus();
                    edit_phone.setError("Phone number is empty.");
                }

            }else{
                edit_email.requestFocus();
                edit_email.setError("E-mail is empty.");
            }

        }else{
            edit_fullname.requestFocus();
            edit_fullname.setError("Full name is empty.");
        }

    }

    private void registerUser(User user){

        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                String idUser = task.getResult().getUser().getUid();
                user.setId(idUser);

                user.save();
                finish();
                startActivity(new Intent(this, MainActivity.class));

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
        //Setting a new name to toolbar_back
        TextView text_titulo = findViewById(R.id.text_title_toolbar);
        text_titulo.setText("Create your Account");

        //EditText components

        edit_email = findViewById(R.id.edit_email);
        edit_fullname = findViewById(R.id.edit_fullname);
        edit_phone = findViewById(R.id.edit_phone);
        edit_password = findViewById(R.id.edit_password);

        //progressBar work
        progressBar = findViewById(R.id.progressBar);




    }



}