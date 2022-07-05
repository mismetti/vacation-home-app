package com.miladev.vacationhome.model;

import com.google.firebase.database.DatabaseReference;
import com.miladev.vacationhome.helper.FirebaseHelper;

public class Ad {

    private String id;
    private String title;
    private String description;
    private String room;
    private String bathroom;
    private String garage;
    private boolean status;
    private String urlImage;
    private String price;

    public Ad() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());

    }

    public void save(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("ad")
                .child(FirebaseHelper.getFirebaseId())
                .child(this.getId());

        reference.setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
