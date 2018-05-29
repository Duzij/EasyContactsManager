package com.example.duzm00.contactsmanager.model;

import com.squareup.moshi.Json;

/**
 * Created by duzm00 on 29.05.2018.
 */

public class Email {

    private String type;
    private String email;

    public Email(String type, String email) {
        this.type = type;
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
