package com.example.duzm00.contactsmanager.screen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duzm00.contactsmanager.R;
import com.example.duzm00.contactsmanager.model.Contact;
import com.example.duzm00.contactsmanager.model.Phone;
import com.example.duzm00.contactsmanager.network.NetworkManager;

import org.w3c.dom.Text;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends Fragment {

    private NetworkManager networkManager = new NetworkManager();

    public AddContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addContact = view.findViewById(R.id.add_contact_btn);

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send(view);
            }
        });

    }

    private void send(View view) {
        if(view == null)
        {
            return;
        }
        else{

        TextInputLayout first_name_input_layout = view.findViewById(R.id.first_name_input_layout);
        EditText first_name_input = first_name_input_layout.getEditText();

        TextInputLayout last_name_input_layout = view.findViewById(R.id.last_name_input_layout);
        EditText last_name_input = last_name_input_layout.getEditText();

        TextInputLayout phone_input_layout = view.findViewById(R.id.phone_input_layout);
        EditText phone_input = phone_input_layout.getEditText();

            final Contact contact = new Contact();
            if(!TextUtils.isEmpty(first_name_input.toString()))
            {
                contact.setFirstName(first_name_input.getText().toString());
            }
            else{
                first_name_input_layout.setError("First name is required");
                return;
            }

            if(!TextUtils.isEmpty(last_name_input.getText().toString()))
            {
                contact.setLastName(last_name_input.getText().toString());
            }
            else{
                last_name_input_layout.setError("Last name is required");
                return;
            }

            if(!TextUtils.isEmpty(phone_input.getText().toString()))
            {
                Phone phone = new Phone("personal", phone_input.getText().toString());
                contact.setPhones(Arrays.asList(phone));
            }
            else{
                phone_input_layout.setError("Last name is required");
                return;
            }

            Button sendBtn = (Button)view;
            sendBtn.setEnabled(false);

            networkManager.getContactService()
                .createContact(contact)
                .enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if(response.isSuccessful())
                {
                    getActivity().onBackPressed();
                }
                else{
                    Toast.makeText(getContext(), "Request not successeful", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Toast.makeText(getContext(), "Request not successeful", Toast.LENGTH_LONG);
            }
        });
    }}
}
