package com.example.duzm00.contactsmanager.screen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

        import android.support.annotation.NonNull;

        import android.support.annotation.Nullable;

        import android.support.design.widget.TextInputEditText;

        import android.support.design.widget.TextInputLayout;

        import android.support.v4.app.Fragment;

        import android.text.Editable;

        import android.text.TextUtils;
        import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

        import android.view.ViewGroup;

        import android.widget.Button;

        import android.widget.EditText;

        import android.widget.Toast;


        import com.example.duzm00.contactsmanager.R;
        import com.example.duzm00.contactsmanager.model.Contact;
        import com.example.duzm00.contactsmanager.network.NetworkManager;
        import com.squareup.moshi.Moshi;

        import java.util.Arrays;

        import retrofit2.Call;

        import retrofit2.Callback;

        import retrofit2.Response;



/**

 * A simple {@link Fragment} subclass.

 */

public class AddContactFragment extends Fragment {

    private NetworkManager networkManager = new NetworkManager();

    private static final String KEY_CONTACT = "contact";

    private Contact contact;

    public AddContactFragment() {
        // Required empty public constructor
    }

    public static AddContactFragment newInstance(Contact contact) {

        AddContactFragment fragment = new AddContactFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CONTACT, contact);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            contact = (Contact) bundle.getSerializable(KEY_CONTACT);
        }

        setHasOptionsMenu(contact != null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        Button sendButton = view.findViewById(R.id.add_contact_btn);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                send(v);
            }
        });

        if (contact != null) {
            EditText textInputFirstName = view.<TextInputLayout>findViewById(R.id.first_name_input_layout).getEditText();
            EditText textInputLastName = view.<TextInputLayout>findViewById(R.id.last_name_input_layout).getEditText();
            EditText textInputPhone = view.<TextInputLayout>findViewById(R.id.phone_input_layout).getEditText();
            textInputFirstName.setText(contact.getFirstName());
            textInputLastName.setText(contact.getLastName());
            if (contact.getPhones() != null && contact.getPhones().size() != 0) {
                textInputPhone.setText(contact.getPhones().get(0).getPhone());
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_delete:
                onDeleteItem();
            return true;
        }

        return false;
    }

    private void onDeleteItem() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Delete contact?")
                .setMessage("Do you really want do delete this contact?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteContact();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        alertDialog.show();
    }


    private void deleteContact() {
        networkManager.getContactService().deleteContact(contact.getUUID())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful())
                        {
                            getActivity().onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(getClass().getSimpleName(), t.toString());
                    }
                });
    }

    private void send(View interactedView) {

        View view = getView();
        if (view == null) {
            return;
        }

        if (!validateInputs(view)) {
            return;
        }

        final TextInputLayout textLayoutFirstName = view.findViewById(R.id.first_name_input_layout);
        EditText textInputFirstName = textLayoutFirstName.getEditText();

        TextInputLayout textLayoutLastName = view.findViewById(R.id.last_name_input_layout);
        EditText textInputLastName = textLayoutLastName.getEditText();

        EditText textInputPhone = view.<TextInputLayout>findViewById(R.id.phone_input_layout).getEditText();

        String firstName = textInputFirstName.getText().toString();
        String lastName = textInputLastName.getText().toString();
        String phoneNumber = textInputPhone.getText().toString();
        Button sendButton = (Button)interactedView;
        sendButton.setEnabled(false);
        Contact newContact;
        if (contact != null) {
            newContact = contact;
        } else {
            newContact = new Contact();
        }
        newContact.update(firstName, lastName, phoneNumber);
        saveContact(newContact);
    }



    private void saveContact(Contact newContact) {
        Call<Contact> call;
        if (newContact.getUUID() == null) {
            call = networkManager.getContactService()
                    .createContact(newContact);
        } else {
            call = networkManager.getContactService()
                    .updateContact(newContact.getUUID(), newContact);
        }

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()) {
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getContext(), "Request not successful", Toast.LENGTH_LONG).show();;
                }

            }


            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
            }

        });

    }



    private boolean validateInputs(View view) {
        final TextInputLayout textLayoutFirstName = view.findViewById(R.id.first_name_input_layout);
        EditText textInputFirstName = textLayoutFirstName.getEditText();

        TextInputLayout textLayoutLastName = view.findViewById(R.id.last_name_input_layout);
        EditText textInputLastName = textLayoutLastName.getEditText();

        if (TextUtils.isEmpty(textInputFirstName.getText().toString())) {
            textLayoutFirstName.setError("First name is required");
            return false;
        } else {
            textLayoutFirstName.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(textInputLastName.getText().toString())) {
            textLayoutLastName.setError("Last name is required");
            return false;
        } else {
            textLayoutLastName.setErrorEnabled(false);
        }

        return true;
    }
}