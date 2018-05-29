package com.example.duzm00.contactsmanager.network;

import com.example.duzm00.contactsmanager.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by duzm00 on 29.05.2018.
 */

public interface IContactService {

    @GET("contact")
    Call<List<Contact>> getContactList();

}
