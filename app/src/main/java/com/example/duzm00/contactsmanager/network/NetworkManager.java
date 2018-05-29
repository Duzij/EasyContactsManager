package com.example.duzm00.contactsmanager.network;

import com.example.duzm00.contactsmanager.model.Contact;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by duzm00 on 29.05.2018.
 */

public class NetworkManager {

    private Retrofit retrofit;
    private IContactService contactService;

    public NetworkManager(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://contactee.jankrecek.cz/test@example.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        contactService = retrofit.create(IContactService.class);

    }

    public IContactService getContactService() {
        return contactService;
    }
}
