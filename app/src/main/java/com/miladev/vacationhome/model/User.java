package com.miladev.vacationhome.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.miladev.vacationhome.helper.FirebaseHelper;

public class User {

    private String id;
    private String fullname;
    private String email;
    private String phone;
    private String password;

    public void save(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("users")
                .child(this.getId());
        reference.setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
