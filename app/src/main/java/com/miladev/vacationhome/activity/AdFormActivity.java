package com.miladev.vacationhome.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.miladev.vacationhome.R;
import com.miladev.vacationhome.helper.FirebaseHelper;
import com.miladev.vacationhome.model.Ad;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class AdFormActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 100;

    private EditText ad_title;
    private EditText ad_desc;
    private EditText ad_room;
    private EditText ad_bath;
    private EditText ad_garage;
    private EditText ad_price;
    private CheckBox check_available;
    private ProgressBar progressBar;

    private ImageView img_ad;
    private String imagePath;
    private Bitmap image;

    private Ad ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_form);

        startComponents();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            ad = (Ad) bundle.getSerializable("ad");

            configData();
        }


        configClick();
    }

    private void configData() {

        Picasso.get().load(ad.getUrlImage()).into(img_ad);
        ad_title.setText(ad.getTitle());
        ad_desc.setText(ad.getDescription());
        ad_bath.setText(ad.getBathroom());
        ad_garage.setText(ad.getGarage());
        ad_room.setText(ad.getRoom());
        ad_price.setText(ad.getPrice());
        check_available.setChecked(ad.isStatus());

    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);


    }

    public void verifyPermissionGallery(View view){

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(AdFormActivity.this, "Denied", Toast.LENGTH_SHORT).show();
            }
        };

        showDialogGalleryPermission(permissionListener, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
        
    }

    private void showDialogGalleryPermission(PermissionListener listener, String[] permissions){
        TedPermission.create()
                .setPermissionListener(listener)
                .setDeniedMessage("If you reject permission, you can not use this service.")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void configClick(){
        findViewById(R.id.save).setOnClickListener(view -> validateData());
        findViewById(R.id.imageB_back).setOnClickListener(view -> finish());
    }

    private void validateData(){
        String title = ad_title.getText().toString();
        String desc = ad_desc.getText().toString();
        String room = ad_room.getText().toString();
        String bath = ad_bath.getText().toString();
        String garage = ad_garage.getText().toString();
        String price = ad_price.getText().toString();

        if (!title.isEmpty()){

            if (!desc.isEmpty()){

                if (!room.isEmpty()){

                    if (!bath.isEmpty()){

                        if (!garage.isEmpty()){

                            if (!price.isEmpty()){

                                if(ad == null) ad = new Ad();
                                ad.setTitle(title);
                                ad.setDescription(desc);
                                ad.setRoom(room);
                                ad.setBathroom(bath);
                                ad.setGarage(garage);
                                ad.setStatus(check_available.isChecked());
                                ad.setPrice(price);
                                
                                if(imagePath != null){
                                    saveImageAd();
                                }else{
                                    if (ad.getUrlImage() != null){
                                        ad.save();
                                    }else{
                                        Toast.makeText(this, "Select a image for ad.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }else{

                                ad_price.requestFocus();
                                ad_price.setError("Price is not defined");

                            }

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

    private void saveImageAd(){

        progressBar.setVisibility(View.VISIBLE);

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("image")
                .child("ad")
                .child(ad.getId() + ".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(imagePath));
        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {

            String urlImage = task.getResult().toString();
            ad.setUrlImage(urlImage);

            ad.save();

            finish();

        })).addOnFailureListener(e ->{
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void startComponents(){

        ad_title = findViewById(R.id.ad_title);
        ad_desc = findViewById(R.id.ad_desc);
        ad_room = findViewById(R.id.ad_room);
        ad_bath = findViewById(R.id.ad_bath);
        ad_garage = findViewById(R.id.qt_garage);
        ad_price = findViewById(R.id.ad_price);
        check_available = findViewById(R.id.check_available);
        img_ad = findViewById(R.id.img_ad);
        progressBar = findViewById(R.id.progressBar);

        TextView text_titulo = findViewById(R.id.text_title_toolbar);
        text_titulo.setText("New Ad");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_GALLERY){

                Uri pathImageSelected = data.getData();
                imagePath = pathImageSelected.toString();

                if (Build.VERSION.SDK_INT < 28){
                    try {
                        image = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), pathImageSelected);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ImageDecoder.Source source = ImageDecoder.createSource(getBaseContext().getContentResolver(), pathImageSelected);
                    try {
                        image = ImageDecoder.decodeBitmap(source);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                img_ad.setImageBitmap(image);
            }
        }
    }
}