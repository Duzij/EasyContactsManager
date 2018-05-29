package com.example.duzm00.contactsmanager.screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duzm00.contactsmanager.R;
import com.example.duzm00.contactsmanager.adapter.ContactListAdapter;
import com.example.duzm00.contactsmanager.model.Contact;
import com.example.duzm00.contactsmanager.network.NetworkManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment {

    private NetworkManager networkManager = new NetworkManager();

    public ContactListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView contactRecycleView = view.findViewById(R.id.fragmentRecyclerView);
        contactRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ContactListAdapter adapter = new ContactListAdapter();
        contactRecycleView.setAdapter(adapter);

        networkManager.getContactService().getContactList().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                adapter.setContacts(response.body());
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.i("error", call.toString());
            }
        });


    }
}
