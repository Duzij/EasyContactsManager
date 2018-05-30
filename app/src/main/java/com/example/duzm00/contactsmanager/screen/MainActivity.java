package com.example.duzm00.contactsmanager.screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.duzm00.contactsmanager.R;
import com.example.duzm00.contactsmanager.model.Contact;

public class MainActivity extends AppCompatActivity implements ContactListFragment.OnContactListFragmentIntegrationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpListFragment();

    }

    private void setUpListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, new ContactListFragment())
                .commit();
    }

    @Override
    public void onCreateContact() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, new AddContactFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEditContact(Contact contact) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, AddContactFragment.newInstance(contact))
                .addToBackStack(null)
                .commit();
    }
}
