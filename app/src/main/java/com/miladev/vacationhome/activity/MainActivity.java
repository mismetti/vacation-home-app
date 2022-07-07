package com.miladev.vacationhome.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.miladev.vacationhome.R;
import com.miladev.vacationhome.autenticacao.LoginActivity;
import com.miladev.vacationhome.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageB_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startComponents();
        configClick();
    }

    private void configClick(){
        imageB_menu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, imageB_menu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_home, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if(menuItem.getItemId() == R.id.menu_filter){
                    startActivity(new Intent(this, FilterAdActivity.class));
                }else if(menuItem.getItemId() == R.id.menu_my_ads){
                    if(FirebaseHelper.getIsAuth()){
                        startActivity(new Intent(this, MyAdActivity.class));
                    }else{
                       //Show message
                        showDialogLogin();
                    }

                }else if (menuItem.getItemId() == R.id.menu_myaccount){
                    if(FirebaseHelper.getIsAuth()){
                        startActivity(new Intent(this, MyAccountActivity.class));
                    }else{
                        //Show message
                        showDialogLogin();
                    }

                }
                return true;
            });
            popupMenu.show();
        });
    }

    private void showDialogLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Auth required");
        builder.setMessage("Log-in to your account to see this page.");
        builder.setCancelable(false);
        builder.setNegativeButton("No", (dialog,  which) -> dialog.dismiss());
        builder.setPositiveButton("Ok", (dialog, which) -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startComponents(){
        imageB_menu = findViewById(R.id.imageB_menu);
    }

}