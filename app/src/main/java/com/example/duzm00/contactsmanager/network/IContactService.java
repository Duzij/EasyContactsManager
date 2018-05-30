package com.example.duzm00.contactsmanager.network;

import com.example.duzm00.contactsmanager.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by duzm00 on 29.05.2018.
 */

public interface IContactService {

    @GET("contact")
    Call<List<Contact>> getContactList();

    @POST("contact")
    Call<Contact> createContact(@Body Contact contact);

    @PATCH("contact/{contact_id}")
    Call<Contact> updateContact(@Path("contact_id") String contactId, @Body Contact contact);

    @DELETE("contact/{contact_id}")
    Call<Void> deleteContact(@Path("contact_id") String contactId);
}
